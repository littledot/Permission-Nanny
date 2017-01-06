package com.permissionnanny

/**
 *
 */

/**
 * Information you can retrieve about a particular security permission
 * known to the system.  This corresponds to information collected from the
 * AndroidManifest.xml's &lt;permission&gt; tags.
 */
class PermissionInfo {

    companion object {
        /**
         * A normal application value for [.protectionLevel], corresponding
         * to the `normal` value of
         * [android.R.attr.protectionLevel].
         */
        val PROTECTION_NORMAL = 0

        /**
         * Dangerous value for [.protectionLevel], corresponding
         * to the `dangerous` value of
         * [android.R.attr.protectionLevel].
         */
        val PROTECTION_DANGEROUS = 1

        /**
         * System-level value for [.protectionLevel], corresponding
         * to the `signature` value of
         * [android.R.attr.protectionLevel].
         */
        val PROTECTION_SIGNATURE = 2

        /**
         * System-level value for [.protectionLevel], corresponding
         * to the `signatureOrSystem` value of
         * [android.R.attr.protectionLevel].
         */
        val PROTECTION_SIGNATURE_OR_SYSTEM = 3

        /**
         * Additional flag for [.protectionLevel], corresponding
         * to the `system` value of
         * [android.R.attr.protectionLevel].
         */
        val PROTECTION_FLAG_SYSTEM = 0x10

        /**
         * Additional flag for [.protectionLevel], corresponding
         * to the `development` value of
         * [android.R.attr.protectionLevel].
         */
        val PROTECTION_FLAG_DEVELOPMENT = 0x20

        /**
         * Additional flag for [.protectionLevel], corresponding
         * to the `appop` value of
         * [android.R.attr.protectionLevel].
         */
        val PROTECTION_FLAG_APPOP = 0x40

        /**
         * Mask for [.protectionLevel]: the basic protection type.
         */
        val PROTECTION_MASK_BASE = 0xf

        /**
         * Mask for [.protectionLevel]: additional flag bits.
         */
        val PROTECTION_MASK_FLAGS = 0xf0

        /**
         * Flag for [.flags], corresponding to `costsMoney`
         * value of [android.R.attr.permissionFlags].
         */
        val FLAG_COSTS_MONEY = 1 shl 0

        /** @hide
         */
        fun fixProtectionLevel(level: Int): Int {
            var level = level
            if (level == PROTECTION_SIGNATURE_OR_SYSTEM) {
                level = PROTECTION_SIGNATURE or PROTECTION_FLAG_SYSTEM
            }
            return level
        }

        /** @hide
         */
        fun protectionToString(level: Int): String {
            var protLevel = "????"
            when (level and PROTECTION_MASK_BASE) {
                android.content.pm.PermissionInfo.PROTECTION_DANGEROUS -> protLevel = "dangerous"
                android.content.pm.PermissionInfo.PROTECTION_NORMAL -> protLevel = "normal"
                android.content.pm.PermissionInfo.PROTECTION_SIGNATURE -> protLevel = "signature"
                android.content.pm.PermissionInfo.PROTECTION_SIGNATURE_OR_SYSTEM -> protLevel = "signatureOrSystem"
            }
            if (level and android.content.pm.PermissionInfo.PROTECTION_FLAG_SYSTEM != 0) {
                protLevel += "|system"
            }
            if (level and android.content.pm.PermissionInfo.PROTECTION_FLAG_DEVELOPMENT != 0) {
                protLevel += "|development"
            }
            if (level and android.content.pm.PermissionInfo.PROTECTION_FLAG_APPOP != 0) {
                protLevel += "|appop"
            }
            return protLevel
        }

    }
}
