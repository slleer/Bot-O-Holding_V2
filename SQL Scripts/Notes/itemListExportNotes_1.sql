use DnDImports
/*
select distinct
	a.name
	,ISJSON(a.name,value) as nameJson
--	,A.Text
	,ISJSON(a.text) as textJson
	,case when len(a.text) > 496 then substring(A.Text,1, 500) + '...' else a.text end as description
	,substring(A.Weight,1, len(a.weight) - charindex(' ', reverse(A.Weight))) as Weight
	,substring(A.Weight,len(a.weight) - charindex(' ', reverse(A.Weight))+2, charindex(' ', reverse(A.Weight))+1) as weight_unit
	,substring(a.value, 1, charIndex(' ', a.value)) as Value
	,substring(a.value, charIndex(' ', a.value), len(a.value) - charIndex(' ', a.value) +1) as value_unit
	, len(a.text) as num_chars
	, case when d.Column5 is not null then 'Y' else 'N' end as hasShort
	, DATALENGTH(a.text) as data_size
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
from dbo.ItemsAll a
left join dbo.MythicalInkItems b join dbo.Currency$ z on z.coin = b.coin on a.name = b.name
left join dbo.MagicItems c join dbo.SourceLegend l on l.Published = c.Source on c.[Item Name] = a.Name
left join dbo.MagicItemsVarious d on d.Column1 = a.Name
left join dbo.SourceLegend y on y.Published = a.Source
where a.exclude <> 1
--order by data_size desc
--for json auto, INCLUDE_NULL_VALUES;
*/


select distinct
	a.name
	,len(a.name) as name_len
	,case when len(a.text) > 496 then substring(A.Text,1, 496) + '...' else a.text end as description
	,a.weightValue as weight
	,a.weightUnit
	,a.costValue as value
	,a.costUnit as valueUnit
from DndImports.dbo.ItemsAll a
left join DndImports.dbo.MythicalInkItems b join DndImports.dbo.Currency$ z on z.coin = b.coin on a.name = b.name
left join DndImports.dbo.MagicItems c join DndImports.dbo.SourceLegend l on l.Published = c.Source on c.[Item Name] = a.Name
left join DndImports.dbo.MagicItemsVarious d on d.Column1 = a.Name
left join DndImports.dbo.SourceLegend y on y.Published = a.Source
where a.exclude <> 1
order by 2 desc
FOR JSON PATH--, INCLUDE_NULL_VALUES


/* previous version
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
FOR JSON PATH, INCLUDE_NULL_VALUES
*/




/*
   SELECT
    JSON_MODIFY(
        (select distinct
				a.name
				,case when len(a.text) > 496 then substring(A.Text,1, 500) + '...' else a.text end as description
				,substring(A.Weight,1, len(a.weight) - charindex(' ', reverse(A.Weight))) as Weight
				,substring(A.Weight,len(a.weight) - charindex(' ', reverse(A.Weight))+2, charindex(' ', reverse(A.Weight))+1) as weight_unit
				,substring(a.value, 1, charIndex(' ', a.value)) as Value
				,substring(a.value, charIndex(' ', a.value), len(a.value) - charIndex(' ', a.value) +1) as value_unit
			from DndImports.dbo.ItemsAll a
			left join DndImports.dbo.MythicalInkItems b join DndImports.dbo.Currency$ z on z.coin = b.coin on a.name = b.name
			left join DndImports.dbo.MagicItems c join DndImports.dbo.SourceLegend l on l.Published = c.Source on c.[Item Name] = a.Name
			left join DndImports.dbo.MagicItemsVarious d on d.Column1 = a.Name
			left join DndImports.dbo.SourceLegend y on y.Published = a.Source
			where a.exclude <> 1
            FOR JSON PATH, INCLUDE_NULL_VALUES, WITHOUT_ARRAY_WRAPPER
        ),
        '$.',
        JSON_QUERY(
            (select distinct
				a.name
				,case when len(a.text) > 496 then substring(A.Text,1, 500) + '...' else a.text end as description
				,substring(A.Weight,1, len(a.weight) - charindex(' ', reverse(A.Weight))) as Weight
				,substring(A.Weight,len(a.weight) - charindex(' ', reverse(A.Weight))+2, charindex(' ', reverse(A.Weight))+1) as weight_unit
				,substring(a.value, 1, charIndex(' ', a.value)) as Value
				,substring(a.value, charIndex(' ', a.value), len(a.value) - charIndex(' ', a.value) +1) as value_unit
			from DndImports.dbo.ItemsAll a
			left join DndImports.dbo.MythicalInkItems b join DndImports.dbo.Currency$ z on z.coin = b.coin on a.name = b.name
			left join DndImports.dbo.MagicItems c join DndImports.dbo.SourceLegend l on l.Published = c.Source on c.[Item Name] = a.Name
			left join DndImports.dbo.MagicItemsVarious d on d.Column1 = a.Name
			left join DndImports.dbo.SourceLegend y on y.Published = a.Source
			where a.exclude <> 1
                FOR JSON PATH, INCLUDE_NULL_VALUES, WITHOUT_ARRAY_WRAPPER
            )
        )
    ) AS JsonData
    FOR JSON PATH, WITHOUT_ARRAY_WRAPPER;
*/