package com.dunystudios.hytale.plugins.config;

public class ConfigData {
    public Chat chat;
    public Balance balance;
    public Messages messages;

    public static class Chat {
        public String currency;
        public int decimal_places;
    }

    public static class Balance {
        public float starting;
        public float maximum;
        public float minimum;
    }

    public static class Messages {
        public String money_sent;
        public String money_received;
        public String insufficient_funds;
        public String balance_check;
        public String unknown_player;
        public String cannot_pay_self;
        public String max_balance_reached;
        public String min_balance_reached;
        public String admin_add_success;
        public String admin_remove_success;
        public String admin_set_success;
        public String error_occurred;
    }
}
