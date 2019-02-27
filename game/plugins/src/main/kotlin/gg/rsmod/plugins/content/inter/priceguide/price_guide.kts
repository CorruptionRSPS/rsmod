package gg.rsmod.plugins.content.inter.priceguide

import gg.rsmod.plugins.api.ext.getInteractingOption
import gg.rsmod.plugins.api.ext.getInteractingSlot
import gg.rsmod.plugins.api.ext.player
import gg.rsmod.plugins.api.ext.searchItemInput

on_button(interfaceId = 387, component = 19) {
    val p = player
    if (!p.lock.canInterfaceInteract()) {
        return@on_button
    }
    PriceGuide.open(player)
}

on_interface_close(interfaceId = PriceGuide.INTERFACE_ID) {
    PriceGuide.close(player)
}

on_button(interfaceId = PriceGuide.TAB_INTERFACE_ID, component = 0) {
    suspendable {
        PriceGuide.add(it, it.getInteractingSlot(), it.getInteractingOption())
    }
}

on_button(interfaceId = PriceGuide.INTERFACE_ID, component = 2) {
    suspendable {
        PriceGuide.remove(it, it.getInteractingSlot(), it.getInteractingOption())
    }
}

on_button(interfaceId = PriceGuide.INTERFACE_ID, component = 10) {
    PriceGuide.depositInventory(player)
}

on_button(interfaceId = PriceGuide.INTERFACE_ID, component = 5) {
    suspendable {
        val item = it.searchItemInput("Select an item to ask about its price:")
        PriceGuide.search(player, item)
    }
}