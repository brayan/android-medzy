package br.com.sailboat.medzy.helper.model;

import android.content.Context;

import br.com.sailboat.medzy.model.Medication;
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite;

public class MedicationModelHelper {

    public static int NEW_MED_ID = -1;

    public static void saveOrUpdateMed(Context ctx, Medication medication) {
        if (isMedNew(medication)) {
            saveNewMed(ctx, medication);
        } else {
            updateMed(ctx, medication);
        }
    }

    public static void saveNewMed(Context ctx, Medication medication) {
        long id = new MedicationSQLite(ctx).saveAndGetId(medication);
        medication.setId(id);
    }

    public static void updateMed(Context ctx, Medication medication) {
        new MedicationSQLite(ctx).update(medication);
    }

    public static boolean isMedNotNew(Medication medication) {
        return !isMedNew(medication);
    }

    public static boolean isMedNew(Medication medication) {
        return (medication.getId() == NEW_MED_ID);
    }

    public static boolean isOutOfStock(double totalAmount) {
        if (totalAmount == 0) {
            return true;
        }

        return false;
    }

    public static boolean isOutOfStock(double totalAmount, double alarmAmount) {
        if (isOutOfStock(totalAmount)) {
            return true;
        }

        if (totalAmount - alarmAmount < 0) {
            return true;
        }

        return false;
    }

}
