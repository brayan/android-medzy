package br.com.sailboat.elseapp.common.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class AlarmManagerHelper {

//    private Context appContext;
//    private Intent intentAlarme;
//    private PendingIntent pendingIntentAlarme;
//    private AlarmManager alarmManager;
//
//    private AlarmManagerHelper(Context context) {
//        this.appContext = context.getApplicationContext();
//    }
//
//    public static void setAlarm(Context ctx, Task task, Alarm alarm) throws ParseException {
//        new AlarmManagerHelper(ctx).setAlarm(task, alarm);
//    }
//
//    private void setAlarm(Task task, Alarm alarm) throws ParseException {
//        inicializarComponentes(task);
//        definirAlarme(alarm);
//    }
//
//    private void inicializarComponentes(Task task) {
//        inicializarIntentAlarme(task);
//        inicializarPendingIntent(task);
//        inicializarAlarmManager();
//    }
//
//    private void definirAlarme(Alarm alarm) throws ParseException {
//
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
//    }
//
//    private void inicializarAlarmManager() {
//        alarmManager = (AlarmManager) appContext.getSystemService(appContext.ALARM_SERVICE);
//    }
//
//    private void inicializarPendingIntent(Task task) {
//        pendingIntentAlarme = PendingIntent.getBroadcast(appContext, (int) task.getId(), intentAlarme, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//
//    private void inicializarIntentAlarme(Task task) {
//        intentAlarme = new Intent(appContext, AlarmReceiver.class);
//        intentAlarme.putExtra("NOME_TAREFA", task.getName());
//        intentAlarme.putExtra("ID_TAREFA", task.getId());
//        intentAlarme.putExtra("EXTRA_TASK", task);
//    }
//
//    private void setAlarmeNaoRepetitivo(DateTime dataInicial) {
//        alarmManager.set(AlarmManager.RTC_WAKEUP, dataInicial.toDate().getTime(), pendingIntentAlarme);
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
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dataInicial.toDate().getTime(), AlarmManager.INTERVAL_DAY * 7, pendingIntentAlarme);
//    }
//
//    private void setAlarmeTodosOsDias(DateTime dataInicial) {
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, dataInicial.toDate().getTime(), AlarmManager.INTERVAL_DAY, pendingIntentAlarme);
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
//    public static void cancelarAlarme(Context context, Task task) {
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra("NOME_TAREFA", task.getName());
//        intent.putExtra("ID_TAREFA", task.getId());
//
//        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, (int) task.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmIntent.cancel();
//    }
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
