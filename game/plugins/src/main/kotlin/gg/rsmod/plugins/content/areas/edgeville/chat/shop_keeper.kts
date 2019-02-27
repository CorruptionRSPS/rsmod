package gg.rsmod.plugins.content.areas.edgeville.chat

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.plugin.Plugin
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.*

arrayOf(Npcs.SHOP_KEEPER_514, Npcs.SHOP_ASSISTANT_515).forEach { shop ->
    on_npc_option(npc = shop, option = "talk-to") {
        suspendable { dialog(it) }
    }

    on_npc_option(npc = shop, option = "trade") {
        open_shop(player)
    }
}

suspend fun dialog(it: Plugin) {
    it.chatNpc("Can I help you at all?", animation = 567)
    when (it.options("Yes please. What are you selling?", "No thanks.")) {
        1 -> open_shop(it.player)
        2 -> it.chatPlayer("No thanks.", animation = 588)
    }
}

fun open_shop(p: Player) {
    p.openShop("Edgeville General Store")
}