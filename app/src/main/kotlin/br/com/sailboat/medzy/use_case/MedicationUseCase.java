package br.com.sailboat.medzy.use_case;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.Calendar;

import br.com.sailboat.canoe.helper.DateHelper;
import br.com.sailboat.medzy.R;
import br.com.sailboat.medzy.model.Medication;
import br.com.sailboat.medzy.persistence.sqlite.MedicationSQLite;

public class MedicationUseCase {

    public static int NEW_MED_ID = -1;

    public static String getDateMedicationHolder(Context context, Calendar calendar) {
        if (DateHelper.isBeforeToday(calendar) || DateHelper.isAfterTomorrow(calendar)) {
            return DateHelper.getMonthAndDayLong(context, calendar);

        } else {
            return DateHelper.getShortDate(context, calendar);
        }
    }

    public static int getDateTimeMedHolderColor(Context context, Calendar calendar, double totalAmount, double amount) {
        if (MedicationUseCase.isOutOfStock(totalAmount, amount)) {
            return ContextCompat.getColor(context, R.color.md_red_500);

        } else if (DateHelper.isBeforeNow(calendar)) {
            return ContextCompat.getColor(context, R.color.md_grey_500);

        } else {
            return ContextCompat.getColor(context, R.color.md_light_blue_500);
        }
    }

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
