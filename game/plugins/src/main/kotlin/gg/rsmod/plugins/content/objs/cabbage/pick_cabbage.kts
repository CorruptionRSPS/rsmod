package gg.rsmod.plugins.content.objs.cabbage

import gg.rsmod.game.model.entity.DynamicObject
import gg.rsmod.game.model.entity.Entity
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Objs
import gg.rsmod.plugins.api.ext.getInteractingGameObj
import gg.rsmod.plugins.api.ext.player

val RESPAWN_DELAY = 75

on_obj_option(obj = Objs.CABBAGE_1161, option = "pick", lineOfSightDistance = 0) {
    val player = player
    val obj = getInteractingGameObj()

    suspendable {
        val route = player.walkTo(it, obj.tile)
        if (route.success) {
            if (player.inventory.isFull()) {
                player.message("You don't have room for this cabbage.")
                return@suspendable
            }
            if (obj.isSpawned(player.world)) {
                val item = if (player.world.percentChance(5.0)) Items.CABBAGE_SEED else Items.CABBAGE
                player.animate(827)
                player.inventory.add(item = item)
                player.world.remove(obj)
                player.world.executePlugin(obj) {
                    suspendable {
                        it.wait(RESPAWN_DELAY)
                        world.spawn(DynamicObject(obj))
                    }
                }
            }
        } else {
            player.message(Entity.YOU_CANT_REACH_THAT)
        }
    }
}