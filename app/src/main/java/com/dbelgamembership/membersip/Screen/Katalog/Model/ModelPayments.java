package com.dbelgamembership.membersip.Screen.Katalog.Model;

public class ModelPayments {

    String payment_type;
    String option_bank;
    String options_account;
    String charge;
    String charge_amount;
    String total;
    String total_bersih;
    String card_holder;

    public ModelPayments() {
    }

    public ModelPayments(String payment_type, String option_bank, String options_account, String charge, String charge_amount, String total, String total_bersih, String card_holder) {
        this.payment_type = payment_type;
        this.option_bank = option_bank;
        this.options_account = options_account;
        this.charge = charge;
        this.charge_amount = charge_amount;
        this.total = total;
        this.total_bersih = total_bersih;
        this.card_holder = card_holder;
    }
}
