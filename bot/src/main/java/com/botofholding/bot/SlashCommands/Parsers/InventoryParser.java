package com.botofholding.bot.SlashCommands.Parsers;

import com.botofholding.contract.DTO.Response.UserSettingsDto;

public interface InventoryParser extends Parser, EphemeralSettingProvider {

    @Override
    default boolean extractEphemeralSetting(UserSettingsDto settings) { return settings.isEphemeralContainer(); }
}
