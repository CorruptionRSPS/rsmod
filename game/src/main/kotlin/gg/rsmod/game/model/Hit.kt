package gg.rsmod.game.model

/**
 * @author Tom <rspsmods@gmail.com>
 */
class Hit private constructor(val hitmarks: List<Hitmark>, val hitbar: Hitbar?, val hitDelay: Int, var damageDelay: Int) {

    data class Hitmark(val type: Int, var damage: Int)

    data class Hitbar(val type: Int, val percentage: Int, val maxPercentage: Int, val depleteSpeed: Int, val delay: Int)

    class Builder {

        private var hitDelay = -1

        private val hitmarks = arrayListOf<Hitmark>()

        private var onlyShowHitbar = false

        private var hideHitbar = false

        private var hitbarType = -1

        private var hitbarDepleteSpeed = -1

        private var hitbarPercentage = -1

        private var hitbarMaxPercentage = -1

        private var hitbarDelay = -1

        private var damageDelay = -1

        fun build(): Hit {
            check(onlyShowHitbar || hitmarks.isNotEmpty()) { "You can't build a Hit with no hitmarkers unless the appropriate flag is set (Builder#addHit or Builder#onlyShowHitbar)" }
            check(!onlyShowHitbar || !hideHitbar) { "You can't have both of these flags set (Builder#hideHitbar | Builder#onlyShowHitbar)" }
            check(hideHitbar || hitbarPercentage != -1) { "Hitbar percentage must be set unless hiding it (Builder#setHitbarPercentage)" }
            check(hideHitbar || hitbarDepleteSpeed == -1 || hitbarDepleteSpeed == 0 || hitbarMaxPercentage != -1) { "Hitbar deplete speed cannot be > 0 unless max percentage is set (Builder#setHitbarMaxPercentage)" }
            check(damageDelay == -1 || damageDelay > 0) { "Damage delay must be positive" }

            if (hitDelay == -1) {
                hitDelay = 0
            }

            if (hitbarType == -1) {
                hitbarType = 0
            }

            if (hitbarDepleteSpeed == -1) {
                hitbarDepleteSpeed = 0
            }

            if (hitbarDelay == -1) {
                hitbarDelay = 0
            }

            if (damageDelay == -1) {
                damageDelay = 0
            }

            val hitbar = if (!hideHitbar) Hitbar(hitbarType, hitbarPercentage, hitbarMaxPercentage, hitbarDepleteSpeed, hitbarDelay) else null
            return Hit(hitmarks, hitbar, hitDelay, damageDelay)
        }

        fun addHit(damage: Int, type: Int): Builder {
            hitmarks.add(Hitmark(type, damage))
            return this
        }

        fun setHitDelay(delay: Int): Builder {
            this.hitDelay = delay
            return this
        }

        fun setDamageDelay(delay: Int): Builder {
            this.damageDelay = delay
            return this
        }

        fun onlyShowHitbar(): Builder {
            this.onlyShowHitbar = true
            return this
        }

        fun hideHitbar(): Builder {
            this.hideHitbar = true
            return this
        }

        fun setHitbarType(type: Int): Builder {
            this.hitbarType = type
            return this
        }

        fun setHitbarDepleteSpeed(depleteSpeed: Int): Builder {
            this.hitbarDepleteSpeed = depleteSpeed
            return this
        }

        fun setHitbarPercentage(percentage: Int): Builder {
            this.hitbarPercentage = percentage
            return this
        }

        fun setHitbarMaxPercentage(percentage: Int): Builder {
            this.hitbarMaxPercentage = percentage
            return this
        }

        fun setHitbarDelay(delay: Int): Builder {
            this.hitbarDelay = delay
            return this
        }
    }
}