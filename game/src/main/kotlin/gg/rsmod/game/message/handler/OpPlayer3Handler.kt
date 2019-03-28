package gg.rsmod.game.message.handler

import gg.rsmod.game.action.PawnPathAction
import gg.rsmod.game.message.MessageHandler
import gg.rsmod.game.message.impl.OpPlayer3Message
import gg.rsmod.game.model.attr.INTERACTING_OPT_ATTR
import gg.rsmod.game.model.attr.INTERACTING_PLAYER_ATTR
import gg.rsmod.game.model.entity.Client
import java.lang.ref.WeakReference

/**
 * @author Triston Plummer ("Dread")
 */
class OpPlayer3Handler : MessageHandler<OpPlayer3Message> {

    override fun handle(client: Client, message: OpPlayer3Message) {
        val index = message.index
        // The interaction option id.
        val option = 3
        // The index of the option in the player's option array.
        val optionIndex = option - 1

        if (!client.lock.canPlayerInteract()) {
            return
        }

        val other = client.world.players[index] ?: return

        if (other.options[optionIndex] == null) {
            return
        }

        log(client, "Player option: name=%s, opt=%d", other.username, option)

        client.attr[INTERACTING_PLAYER_ATTR] = WeakReference(other)
        client.attr[INTERACTING_OPT_ATTR] = option
        client.executePlugin(PawnPathAction.walkPlugin)
    }

}