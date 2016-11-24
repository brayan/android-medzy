package br.com.sailboat.elseapp.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.util.List;

import br.com.sailboat.elseapp.model.Alarm;
import br.com.sailboat.elseapp.receiver.AlarmReceiver;

public class AlarmManagerHelper {

    public static final int REQUEST_POSTPONE = 999999998;
    public static final String ALARM_ID = "ALARM_ID";

    private Context context;
    private Intent intent;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    private long alarmId;
    private long timeInMillis;

    private AlarmManagerHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    public static void setAlarm(Context ctx, long alarmId, long timeInMillis) throws ParseException {
        new AlarmManagerHelper(ctx).setAlarm(alarmId, (int) alarmId, timeInMillis);
    }

    public static void setPostponeAlarm(Context ctx, long alarmId, long timeInMillis) throws ParseException {
        new AlarmManagerHelper(ctx).setAlarm(alarmId, REQUEST_POSTPONE, timeInMillis);
    }

    private void setAlarm(long alarmId, int requestId, long timeInMillis) throws ParseException {
        intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(ALARM_ID, alarmId);
        pendingIntent = PendingIntent.getBroadcast(context, requestId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        getAlarmManager().setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
    }

    private AlarmManager getAlarmManager() {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    private void defineAlarme(Alarm alarm) throws ParseException {



//        Date dataAlarmeInicial = DateHelper.getDate(alarm.getInitialAlarmDate());
//        DateTime dataInicial = new DateTime(dataAlarmeInicial.getTime());
//        DateTime dataProximoAlarme = new DateTime(DateHelper.getDate(alarm.getNextAlarmDate()));
//
//        if (deveSetarAlarmeNaoRepetitivo(alarm.getRepeatType(), dataInicial)) {
//            setAlarmeNaoRepetitivo(dataInicial);
//
//        } else {
//
//            int quantidade = 1;
//
//            while (true) {
//
//                if (deveSetarNovoAlarme(dataInicial, dataProximoAlarme)) {
//                    setNovoAlarmeTipoRepeticao(alarm.getRepeatType(), dataInicial);
//                    break;
//                } else {
//                    dataInicial = getDateTimeAlarmeIncrementado(alarm.getRepeatType(), dataAlarmeInicial, quantidade);
//                    quantidade++;
//                }
//
//            }
//        }
    }



    public static void cancelAlarms(Context context, List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            AlarmManagerHelper.cancelAlarm(context, alarm.getId());
        }
    }

    public static void cancelAlarm(Context context, long alarmId) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("ALARM_ID", alarmId);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, (int) alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmIntent.cancel();
    }

//    private void setAlarmeNaoRepetitivo(DateTime dataInicial) {
//        alarmManager.set(AlarmManager.RTC_WAKEUP, dataInicial.toDate().getTime(), pendingIntent);
//    }
//
//    private boolean deveSetarAlarmeNaoRepetitivo(AlarmRepeatType repeatType, DateTime dataInicial) {
//        return dataInicial.isAfterNow() && repeatType == AlarmRepeatType.NOT_REPEAT;
//    }
//
//    private void setNovoAlarmeTipoRepeticao(AlarmRepeatType repeatType, DateTime dataInicial) {
//        switch (repeatType) {
//            case EVERY_DAY:
//                setAlarmeTodosOsDias(dataInicial);
//                break;
//            case EVERY_WEEK:
//                setAlarmeTodasAsSemanas(dataInicial);
//                break;
//            case EVERY_MONTH:
//
//            case EVERY_YEAR:
//                setAlarmeNaoRepetitivo(dataInicial);
//                break;
//        }
//    }
//
//    private void setAlarmeTodasAsSemanas(DateTime dataInicial) {
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dataInicial.toDate().getTime(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
//    }
//
//    private void setAlarmeTodosOsDias(DateTime dataInicial) {
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dataInicial.toDate().getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
//    }
//
//    private boolean deveSetarNovoAlarme(DateTime dataInicial, DateTime dataProximoAlarme) {
//        return dataInicial.isAfterNow() && (dataInicial.isAfter(dataProximoAlarme) || dataInicial.isEqual(dataProximoAlarme));
//    }
//
//    public static void setAlarmeAtualizacaoTarefas(Context context) {
//        Context appContext = context.getApplicationContext();
//
//        Intent intent = new Intent(appContext, AlarmUpdateReceiver.class);
//
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(appContext, 999999999, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
//
//        try {
//            Date dataInicial = DateHelper.getDate(DateHelper.getInitialDateTomorrow());
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dataInicial.getTime(), AlarmManager.INTERVAL_DAY, alarmIntent);
//
//        } catch (ParseException e) {
//            LogHelper.printExceptionLog(e);
//        }
//    }
//

//
//    @NonNull
//    public static DateTime getDateTimeAlarmeIncrementado(AlarmRepeatType repeatType, Date dataAlarme, int quantidade) {
//
//        DateTime dateTime = new DateTime(dataAlarme);
//
//        switch (repeatType) {
//            case EVERY_DAY:
//                dateTime = dateTime.plusDays(quantidade);
//                break;
//            case EVERY_WEEK:
//                dateTime = dateTime.plusWeeks(quantidade);
//                break;
//            case EVERY_MONTH:
//                dateTime = dateTime.plusMonths(quantidade);
//                break;
//            default:
//                dateTime = dateTime.plusYears(quantidade);
//                break;
//        }
//
//        return dateTime;
//    }


}
