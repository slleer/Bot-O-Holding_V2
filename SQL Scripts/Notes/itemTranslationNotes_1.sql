use DnDImports
select top 5 * from DnDImports.dbo.mythicalInkItems where name = 'Bag of Holding'
select top 5 * from DnDImports.dbo.itemsall where name = 'Bag of Holding'
select top 5 * from DnDImports.dbo.magicitemsvarious where column1 = 'Bag of Holding'
select top 5 * from DnDImports.dbo.magicItems where [item name] = 'Bag of Holding'
select distinct Rarity from DnDImports.dbo.mythicalinkitems
select distinct rarity from DnDImports.dbo.itemsall
select distinct column3 from DnDImports.dbo.magicitemsvarious

select * 
from DnDImports.dbo.ItemsAll a 
join DnDImports.dbo.MythicalInkItems b on a.name = b.name
where a.name like 'Bag%'

select * 
from DnDImports.dbo.Containers a
join DnDImports.dbo.itemsAll b on a.column1 = b.name

select * from DnDImports.dbo.itemsall where name like 'Backpack%'

select distinct type from DnDImports.dbo.magicItems order by type

/*
update ia
set rarity = upper(rarity)
from DnDImports.dbo.ItemsAll ia
where ia.Rarity = 'unknown'
*/
with preProcess as (
select distinct
	a.name
	,case when len(a.text) > 496 then substring(A.Text,1, 500) + '...' else a.text end as description
	,substring(A.Weight,1, len(a.weight) - charindex(' ', reverse(A.Weight))) as weight
	,substring(A.Weight,len(a.weight) - charindex(' ', reverse(A.Weight))+2, charindex(' ', reverse(A.Weight))+1) as weightUnit
	,substring(a.value, 1, charIndex(' ', a.value)) as value
	,substring(a.value, charIndex(' ', a.value), len(a.value) - charIndex(' ', a.value) +1) as valueUnit
from DndImports.dbo.ItemsAll a
left join DndImports.dbo.MythicalInkItems b join DndImports.dbo.Currency$ z on z.coin = b.coin on a.name = b.name
left join DndImports.dbo.MagicItems c join DndImports.dbo.SourceLegend l on l.Published = c.Source on c.[Item Name] = a.Name
left join DndImports.dbo.MagicItemsVarious d on d.Column1 = a.Name
left join DndImports.dbo.SourceLegend y on y.Published = a.Source
where a.exclude <> 1
) select *
from preProcess
where weight like '%[^0-9.]%'
or value like '%[^0-9.]%'


select rarity 
from DnDImports.dbo.itemsall miv
where rarity in ('artifact','common','legendary','rare','uncommon','very rare')

select * 
from DnDImports.dbo.itemsall a
left join MythicalInkItems b on a.Name = b.name
left join DnDImports.dbo.magicitemsvarious c on a.Name = c.Column1
left join MagicItems d on d.[Item Name] = a.Name

select name, count(text) as num_items from DnDImports.dbo.itemsall group by name order by num_items desc
select name, count(name) as num_items from MythicalInkItems group by name order by num_items desc
select [Item Name] from MagicItems group by [Item Name] having count([Item Name]) > 1
select Column1, count(Column1) as num_items from DnDImports.dbo.magicitemsvarious group by Column1 order by num_items desc



select * from DndImports.dbo.ItemsAll where name in (select [Item Name] from MagicItems group by [Item Name] having count([Item Name]) > 1)
select * from DndImports.dbo.MythicalInkItems where name in (select [Item Name] from MagicItems group by [Item Name] having count([Item Name]) > 1)
select * from DnDImports.dbo.MythicalInkItems where name like '%healing%'
select * from DnDImports.dbo.ItemsAll where name like '%ioun%'
select name from DnDImports.dbo.ItemsAll

select * from DnDImports.dbo.ItemsAll a
left join DnDImports.dbo.ItemsAll b on a.name like concat(b.name, '%')
where b.rarity = 'varies' and a.rarity <> 'varies'


select distinct b.name, 'ItemsAll' as sourceTable
into #potentialExcludes
from DnDImports.dbo.ItemsAll a
left join DnDImports.dbo.ItemsAll b on a.name like concat(b.name, '%')
where b.rarity = 'varies' and a.rarity <> 'varies'


select a.* 
from DnDImports.dbo.ItemsAll a
left join #potentialExcludes b on a.Name = b.Name
where Rarity = 'varies'
and a.name not in ('Coin of Decisionry','Documancy Satchel','Living Loot Satchel', 'Obviator''s Lenses','Occultant Abacus','Portfolio Keeper','Whisper Jar')
and b.Name is null
order by a.name 

select * from DnDImports.dbo.ItemsAll where name like '%Wraps of Unarmed Power%'

update ia
set rarity = 'UNKNOWN'
from DnDImports.dbo.ItemsAll ia
where ia.name = 'Gnomengarde Grenade'

select name,
Rarity
from DnDImports.dbo.ItemsAll ia
where ia.name = 'Gnomengarde Grenade'



--insert into #potentialExcludes (name, sourceTable)
select name, 'ItemsAll' from DnDImports.dbo.ItemsAll where name like 'Wraps of Unarmed Power'

select * from #potentialExcludes a
join DnDImports.dbo.MagicItems b on a.Name = b.[Item Name]
order by a.name, b.Rarity

select *
from DnDImports.dbo.ItemsAll a
join MagicItems b on b.[Item Name] = a.Name
left join #potentialExcludes c on c.Name = b.[Item Name] 
where c.Name is null

select * from DnDImports.dbo.ItemsAll order by name
select * from DnDImports.dbo.MythicalInkItems where name = 'Abracadabrus'
--Bolts (20)	PHB'24	222	none	ammunition	NULL	NULL	NULL	NULL	1½ lb.	1 gp	Crossbow bolts are used with a weapon that has the ammunition property to make a ranged attack. Each time you attack with the weapon, you expend one piece of ammunition. Drawing the ammunition from a quiver, case, or other container is part of the attack (you need a free hand to load a one-handed weapon). At the end of the battle, you can recover half your expended ammunition by taking a minute to search the battlefield.Bolts are typically stored in a Crossbow Bolt Case (bought separately).
update ia
set Properties = replace(Properties, '�', '')
from DnDImports.dbo.ItemsAll ia
where ia.Properties like '%�%'

select Properties, replace(Properties, '�', '')
from DnDImports.dbo.ItemsAll ia
--join DnDImports.dbo.MythicalInkItems b on ia.name = b.name
where ia.Properties like '%�%'


select * from Currency$

update DnDImports.dbo.MythicalInkItems
set coin = 'Copper'

select distinct value from DnDImports.dbo.ItemsAll order by Value
select * from DnDImports.dbo.ItemsAll where Damage like '%d%' and category not in ('WEAPON', 'VEHICLE','ARMOR','MOUNT') 

update a
set a.Properties = Damage
from DnDImports.dbo.ItemsAll a 
where category = 'MOUNT'


select CONCAT(Damage, ', ' + Properties)
from DnDImports.dbo.ItemsAll a 
where Damage like '%d%' and category not in ('WEAPON', 'VEHICLE','ARMOR','MOUNT') 
select * from DnDImports.dbo.ItemsAll
where category = 'MOUNT'

-- DnDImports.dbo.itemsall
--
-- Name
-- rarity
-- category		  -- coalesce with mythicalInkItems.category
-- kind (rename)  -- coalesce with mythicalInkitems.image
-- classification -- coalesce with mythicalInkItems.classification
-- Attunement
-- damage (armor and weapons)
-- properties

-- MythicalInkItems
--
-- category
-- classification
-- properties

-- DnDImports.dbo.magicitemsvarious colums to keep
--
-- column5 as short_desc

-- magicItesm colums to keep
--
-- source joined with sourcelegend to produce full source 


select distinct
	a.name
	,a.Rarity
	,a.category
	,a.classification
	,a.kind
	,a.Attunement
	,a.Damage
	,a.Properties
	, 'a--sep--b' as sep1
	, b.category
	, b.classification
	,b.properties
	, 'b--sep--c' as sep2
	, c.Source
	, coalesce(y.Published2, l.Published2) as sourceText
	, 'c--sep--d' as sep3
	, d.Column5 as short_desc
from DnDImports.dbo.ItemsAll a
left join DnDImports.dbo.MythicalInkItems b join DnDImports.dbo.Currency$ z on z.coin = b.coin on a.name = b.name
left join DnDImports.dbo.MagicItems c join DnDImports.dbo.SourceLegend l on l.Published = c.Source on c.[Item Name] = a.Name
left join DnDImports.dbo.MagicItemsVarious d on d.Column1 = a.Name
left join DnDImports.dbo.SourceLegend y on y.Published = a.Source
where a.exclude <> 1
for json auto;


select distinct
	a.name
	,A.Text
	,case when len(a.text) > 496 then substring(A.Text,1, 500) + '...' else a.text end as description
	, len(a.text) as num_chars
	, case when d.Column5 is not null then 'Y' else 'N' end as hasShort
	, DATALENGTH(a.text) as data_size
	,A.Weight
	,A.Value
/*	,a.Rarity
	,a.category
	,a.classification
	,a.kind
	,a.Attunement
	,a.Damage
	,a.Properties
	, 'a--sep--b' as sep1
	, b.category
	, b.classification
	,b.properties
	, 'b--sep--c' as sep2
	, c.Source
	, coalesce(y.Published2, l.Published2) as sourceText
	, 'c--sep--d' as sep3
	, d.Column5 as short_desc
*/
from DnDImports.dbo.ItemsAll a
left join DnDImports.dbo.MythicalInkItems b join DnDImports.dbo.Currency$ z on z.coin = b.coin on a.name = b.name
left join DnDImports.dbo.MagicItems c join DnDImports.dbo.SourceLegend l on l.Published = c.Source on c.[Item Name] = a.Name
left join DnDImports.dbo.MagicItemsVarious d on d.Column1 = a.Name
left join DnDImports.dbo.SourceLegend y on y.Published = a.Source
where a.exclude <> 1
for json auto;


select weight, replace(weight, '1/4', '0.25') from DnDImports.dbo.itemsAll where weight like '%1/9%'

update a
set weight = replace(weight, '1/5', '0.2') from DnDImports.dbo.itemsAll a where weight like '%1/5%'

--Over the centuries since the first Deck of Many Things was created, many have sought and failed to replicate it. But some have created new cards. These forty-four additional cards are known collectively as the Deck of Many More Things. (More information on creating new cards for this deck appears in chapter 2.)Like the Deck of Many Things, the Deck of Many More Things manifests differently on various worlds. While it can include fewer or different cards, it frequently appears with a Deck of Many Things as part of a combined deck of sixty-six illuminated cards. The combined deck is usually protected by a box or pouch. The forty-four cards of the Deck of Many More Things bear similar imagery to those in the Deck of Many Things and have potent magical effects, which are detailed later in this description. Notably, cards from the Deck of Many More Things are more likely to be beneficial, though about a third of them are still dangerous.Before you draw a card, you must declare how many cards you intend to draw and then draw them randomly. Unless a card allows you to draw additional cards, any cards drawn exceeding this number have no effect.As soon as you draw a card, its magic takes effect. You must draw each card you declared no more than 1 hour after the previous draw. Unless a card states otherwise, if you fail to draw the chosen number, the remaining number of cards fly from the deck and take effect simultaneously.Unless it is the Fool or the Jester card, a drawn card immediately takes effect, fades from existence, and reappears in the deck, making it possible to draw the same card multiple times.The DM can use the physical cards provided in The Deck of Many Things card set to build a combined Deck of Many Things and Deck of Many More Things, including whichever cards they desire. Alternatively, roll on the Deck of Many More Things table below to randomly determine what cards are drawn.Deck of Many More Thingsd100Card01Aberration02Balance*03Beast04Book05Bridge06Campfire07Cavern08Celestial09Comet*10Construct11Corpse12Crossroads13Donjon*14Door15Dragon16Elemental17Euryale*18Expert19Fates*20Fey21Fiend22Flames*23Fool*24Gem*25Giant26Humanoid27Jester*28Key*29Knight*30Lance31Mage32Map33Maze34Mine35Monstrosity36Moon*37Ooze38Path39Pit40Plant41Priest42Prisoner43Puzzle*44Ring45Rogue*46Ruin*47Sage*48Shield49Ship50Skull*51Staff52Stairway53Star*54Statue55Sun*56Talons*57Tavern58Temple59Throne*60Tomb61Tower62Tree63Undead64Void*65Warrior66Well67-00Roll again* Found in the Deck of Many Things as depicted in the Dungeon Master's Guide Aberration. You gain telepathy within a range of 90 feet. Beast. You immediately transform into a random Beast with a CR of 5 or lower. Your game statistics�including your ability scores, hit points, and possible actions�are replaced by the Beast's game statistics, and any nonmagical equipment you're wearing or carrying melds into your new form and can't be used. Any magic items you're carrying drop in an unoccupied space within 5 feet of your new form.You remain transformed in this way for 2d12 days; nothing can alter your form while you're under the effects of this card, but the Wish spell can end the transformation early. When you revert to your normal form, you return to the same state you were in when you initially transformed. Book. You gain the ability to speak, read, and write 1d6 + 2 languages of your choice. Bridge. You gain the ability to cast the Time Stop spell 1d3 times. Use your Intelligence, Wisdom, or Charisma as the spellcasting ability (your choice). Campfire. You immediately gain the benefits of finishing a long rest. Cavern. You gain a climbing speed equal to your walking speed. You also gain the ability to move up, down, across vertical surfaces, and along ceilings, while leaving your hands free. Celestial. You sprout a pair of softly luminescent, feathered wings from your back and gain a flying speed of 30 feet. Construct. A homunculus appears in an unoccupied space within 5 feet of you. The appearance of the homunculus is determined by the DM, and the homunculus treats you as its creator. Corpse. You immediately drop to 0 hit points, have the unconscious condition, and must begin making death saving throws. Spells and other magical effects that restore hit points have no effect on you until you are stabilized. If you fail three death saving throws, you die and can be resurrected only by the Wish spell. Crossroads. Roll a d20. If the roll is even, you age 1d10 years. If the roll is odd, you become younger by 1d10 years, to a minimum of 1 year. This effect can be undone only by the Wish spell, divine intervention, or similar magic. Door. You gain the ability to cast the Gate spell 1d4 times, requiring no material components. Use your Intelligence, Wisdom, or Charisma as the spellcasting ability (your choice). Dragon. A dragon egg appears at your feet and immediately hatches into a dragon wyrmling. The type of dragon is chosen by the DM. The wyrmling views you as its parent and is staunchly loyal to you and your allies. Elemental. You become immune to one of the following damage types (choose immediately upon drawing this card): acid, cold, fire, lightning, or thunder. Expert. Your Dexterity score increases by 2, to a maximum of 22. Fey. A fey crossing opens into the Feywild, and you're immediately pulled through it, disappearing in a flash of rainbow-colored light. You draw no more cards.The fey crossing appears as a shimmering fractal of light above the deck, and it remains open for 1 minute after the card is drawn. The precise location in the Feywild to which the fey crossing leads is determined by the DM. Fiend. A powerful Fiend appears in a nearby unoccupied space and offers you a deal. The precise nature of this deal is up to the DM, but usually the Fiend offers some material reward in exchange for you and your allies completing a task for the Fiend. The Fiend is indifferent to you and can be bargained with; it keeps its side of any bargain it makes, though it might twist the wording of any agreement to suit its purposes. If attacked, or if negotiations fail and you refuse the Fiend's offer, it returns to its home plane. Giant. You immediately grow 2d10 inches in height, and your hit point maximum and current hit points both increase by 20. Humanoid. You can immediately choose to stop drawing from the deck, regardless of how many cards you initially declared. Lance. All your ability scores increase by 1, to a maximum of 20. Mage. Your Intelligence score increases by 2, to a maximum of 22. Map. At any time you choose within 1 year of drawing this card, you can mentally name or describe an object or individual that is familiar to you. You immediately know the location of the object or individual, as well as the distance between you and the object or individual, even if the object or individual is on a different plane of existence. If you named an individual, you know if they are alive and any conditions they have. If you named an object, you know if it is broken or not. If you named a magic item that has charges, you know how many charges it has remaining. Maze. You gain 1d3 levels of exhaustion. Mine. A pile of 2d6 gems (each worth 5,000 gp) and 1d10 chunks of precious ore (each worth 2,500 gp) appears at your feet. Monstrosity. A Large or larger Monstrosity with a challenge rating of 10 or less (chosen by the DM) appears in an unoccupied space within 15 feet of you. The creature is hostile toward you and attacks immediately. The creature disappears when it is killed or when you are reduced to 0 hit points. If there isn't enough space for a Large or larger creature to appear, this card has no effect. Ooze. A gelatinous cube immediately appears in your space and engulfs you. The gelatinous cube is hostile and remains until it is destroyed. If there isn't enough space for the gelatinous cube to appear, this card has no effect. Path. Your walking speed increases by 10 feet. Pit. A pit opens beneath you. You plummet 3d6 × 10 feet, take damage from the fall, and have the prone condition. Plant. You gain the ability to cast Speak with Plants without using a spell slot; you must finish a long rest before you can cast it this way again. If you have spell slots of 3rd level or higher, you can cast this spell using them. Use your Intelligence, Wisdom, or Charisma as the spellcasting ability (your choice). Priest. Your Wisdom score increases by 2, to a maximum of 22. Prisoner. Glowing chains made of magical force appear and wrap around you. You have the restrained condition until the chains are destroyed or you are freed. While you have this condition, you can't cast spells, and any magic items you're wearing or carrying have their properties suppressed. You draw no more cards. The chains are immune to damage and can't be dispelled using the Dispel Magic spell or similar magic. However, a Disintegrate spell destroys the chains instantly, freeing you. Another creature can also free you by succeeding on a DC 30 Dexterity check using thieves' tools. Ring. A rare or rarer magic ring appears on your finger. If you have the attunement slots available, you're automatically attuned to the ring when it appears. The DM chooses the ring. Shield. A rare or rarer suit of magic armor that you are proficient with appears in your hands. The DM chooses the armor. If you lack proficiency with any armor, your base AC instead now equals 12 + your Dexterity modifier while you aren't wearing armor. Ship. You gain proficiency in three skills chosen by the DM. Staff. A rare or rarer magic rod, staff, or wand appears in your hands. The DM chooses the item. Stairway. You can choose to either decrease your number of declared draws by two or receive a rare or rarer wondrous item, which appears in your hands. The DM chooses the item. Statue. You immediately have the petrified condition as your body is transformed into marble. The petrification lasts until you are freed with the Greater Restoration spell or similar magic. Tavern. Your Charisma score increases by 2 to a maximum of 22. Temple. A deity or entity of similar power becomes bound to aid you. At any point in time between drawing the card and when you die, you can use your action to call on this entity for divine intervention, and the entity is bound to answer. The parameters and nature of this intervention are chosen by the DM. If you die without having used this intervention, the deity fulfills its obligation by casting the Resurrection spell on you. Once the entity has answered your call for divine intervention or resurrected you, the entity is no longer bound to aid you. Tomb. At any time you choose within 1 year of drawing this card, you can cast the True Resurrection spell once without expending a spell slot or requiring material components. Use your Intelligence, Wisdom, or Charisma as the spellcasting ability (your choice). Tower. Draw two additional cards beyond your declared number of draws. The magic of these cards doesn't immediately take effect; instead, choose one of the two additional cards to keep, returning the other to the deck. The magic of the card you keep takes effect immediately thereafter. Tree. Your skin immediately becomes rough, like tree bark. Your base AC now equals 15 + your Dexterity modifier while you aren't wearing armor, but you have vulnerability to fire damage. This transformation can be undone only by the Wish spell, divine intervention, or similar magic. Undead. Somewhere on the Material Plane, a revenant rises. This revenant blames you for its existence and relentlessly hunts you to exact its revenge. The revenant exists until either 1 year passes, the revenant kills you, or you use a Wish spell to banish it permanently to the afterlife. Warrior. Your Strength score increases by 2 to a maximum of 22. Well. You learn three cantrips of your choice from any spell list.


select * 
from DnDImports.dbo.ItemsAll a
join DnDImports.dbo.MythicalInkItems b on a.Name = b.name
where a.Properties is null and b.properties is not null
and b.category = 'WEAPON'

select * from DnDImports.dbo.ItemsAll where category like 'POI%'

update a
set a.Properties = b.properties,
a.category = b.category
from DnDImports.dbo.ItemsAll a
join DnDImports.dbo.MythicalInkItems b on a.Name = b.name
where a.Properties is null and b.properties is not null
and b.category = 'WEAPON'

select *, case classification when 'Shield' then concat('AC +', ac) else concat('AC ',ac) end as newac
from DnDImports.dbo.MythicalInkItems
where ac is not null

--set a.value = concat((case when b.cost >= 100 then b.cost*TRY_PARSE(z.gp as float) when b.cost >= 10 then b.cost*TRY_PARSE(z.sp as float) else b.cost end), case when b.cost >= 100 then ' gp' when b.cost >= 10 then ' sp' else ' cp'  end)

update a
set a.Damage = b.damage
from DnDImports.dbo.MythicalInkItems b
join DnDImports.dbo.ItemsAll a on a.Name = b.name
where a.name in ('Hammer of Thunderbolts','Oathbow')
and b.cost is not null

select a.*, b.cost
, substring(a.value, 1, charindex(' ', a.value))
, try_parse(substring(a.value, 1, charindex(' ', a.value)) as decimal(10,2))
, try_parse(substring(a.value, 1, charindex(' ', a.value)) as decimal(10,2)) % 1
, concat((case when b.cost >= 100 then b.cost*TRY_PARSE(z.gp as float) when b.cost >= 10 then b.cost*TRY_PARSE(z.sp as float) else b.cost end), case when b.cost >= 100 then ' gp' when b.cost >= 10 then ' sp' else ' cp'  end)
from DnDImports.dbo.MythicalInkItems b
join DnDImports.dbo.ItemsAll a on a.Name = b.name
left join DnDImports.dbo.Currency$ z on z.coin = b.coin
where a.value is not null
and b.cost in (25, 35, 45, 55, 65, 75, 85,95, 15)
and try_parse(substring(a.value, 1, charindex(' ', a.value)) as decimal(10,2)) % 1 > 0

select 3.5 % 1


update ai
	set ai.category = case 
	--when charindex('(', type) > 0 then substring(upper(type), 1, charindex('(', type)-1)
		when charindex(',', type) > 0 then substring(upper(type), 1, CHARINDEX(',', type)-1)
	else upper(type) end
--	, ai.classification = case 
	--when charindex('(', type) > 0 then substring(type, charindex('(',type)+1,charindex(')',type)-charindex('(',type)-1)
--		when charindex(',', type) > 0 then trim(substring(type, charindex(',',type)+1,len(type)-charindex(',',type)))
		--else '' end
from DnDImports.dbo.ItemsAll ai 
where category is null
and exclude = 0
and name not like 'Orb of Shielding%'
and type like '%, generic variant'
and type not like 'adventuring gear, poison%'
--and charindex(',', type) < 1
and type not like '%weapon%'

select
	name
	, type
	, case 
	--when charindex('(', type) > 0 then substring(upper(type), 1, charindex('(', type)-1)
		when charindex(',', type) > 0 then substring(upper(type), 1, CHARINDEX(',', type)-1)
	else upper(type) end as category
	, case 
	--when charindex('(', type) > 0 then substring(type, charindex('(',type)+1,charindex(')',type)-charindex('(',type)-1)
		when charindex(',', type) > 0 then trim(substring(type, charindex(',',type)+1,len(type)-charindex(',',type)))
		else '' end
	--substring(type, charindex('(',type)+1, case when charindex('(', type) > 0 then charindex(')',type)-charindex('(',type)-1 else 0 end)
	as class
from DnDImports.dbo.ItemsAll 
where category is null
and exclude = 0
and name not like 'Orb of Shielding%'
and type like '%, generic variant'
and type not like 'adventuring gear, poison%'
--and charindex(',', type) < 1
order by class desc

select distinct category, classification from DnDImports.dbo.ItemsAll

select * from DnDImports.dbo.ItemsAll
where category is null
and exclude = 0

update DnDImports.dbo.ItemsAll
set category = 'ARMOR',
classification = 'Heavy Armor',
Damage = 'AC +2'
where name like 'Dwarven Plate'
and kind is null

select * from DnDImports.dbo.ItemsAll
where charindex(',', type) > 0
and category is null
and exclude = 0
and type not like 'adventuring gear, poison%'
--and name not like 'Orb of Shielding%'
and type not like '%, generic variant'


select * from MythicalInkItems where classification like 'simple%'

select *
from DnDImports.dbo.ItemsAll 
where name in ('Light Repeating Crossbow', 'War Pick')

update a
set a.kind = 'Crossbow'
from DnDImports.dbo.ItemsAll a
where name in ('Light Repeating Crossbow')

select * from DnDImports.dbo.itemsall where name like 'Elemental Essence Shard%'


--Multiple variations of this item exist, as listed below:Elemental Gem, Blue SapphireElemental Gem, EmeraldElemental Gem, Red CorundumElemental Gem, Yellow Diamond

select * from DnDImports.dbo.SourceLegend l 
--join DnDImports.dbo.ItemsAll i on i.Source = l.Published
order by l.Published2




insert into #potentialExcludes (name, sourceTable)
values ('Elemental Gem', 'ItemsAll')

-- has hit die in damage column, weapons mostly
select * 
from DnDImports.dbo.ItemsAll 
where damage not like 'AC%'
and Damage is not null
and damage not like 'Speed%'

update DnDImports.dbo.ItemsAll
set kind = 'staff',
category = 'WEAPON',
classification = 'simple Weapon'
where name = 'Wooden Staff'

select * 
from DnDImports.dbo.ItemsAll
where name = 'Mind Lash'


select a.*, substring(properties, 1, len(properties) - CHARINDEX('|',REVERSE(properties))) as newProp 
from DnDImports.dbo.MythicalInkItems a
--join DnDImports.dbo.ItemsAll b on a.name = b.Name
where a.Properties like '%requires attunement'

update DnDImports.dbo.MythicalInkItems
set properties = substring(properties, 1, len(properties) - CHARINDEX('|',REVERSE(properties)))
where properties like '%requires attunement'

delete from DnDImports.dbo.SourceLegend where Published = 'VRGR'

select * from #potentialExcludes

select *
from DnDImports.dbo.ItemsAll a
join #potentialExcludes b on a.Name = b.Name


update a
set exclude = 0
from DnDImports.dbo.ItemsAll a
where exclude is null

select * from DnDImports.dbo.SourceLegend order by Published

drop table #potentialExcludes