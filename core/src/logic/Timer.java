package logic;

import java.util.Date;

public class Timer {
    protected long millisecond = 0;
    protected Date startDate;
    protected Date currentDate;
    protected TimerThread timerThread = new TimerThread();

    private class TimerThread extends Thread {
        @Override
        public void run() {
            while(status == Status.RUNNING) {
                Date newDate = new Date();
                millisecond += newDate.getTime() - currentDate.getTime();
                currentDate = newDate;
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    //
                }
            }
        }
    }

    enum Status {
        NOT_STARTED ("NOT STARTED"),
        RUNNING ("RUNNING"),
        STOPPED ("STOPPED"),
        PAUSED ("PAUSED");
        protected String msg;

        Status(String msg) {
            this.msg = msg;
        }
    }
    protected Status status = Status.NOT_STARTED;

    Timer(){}

    public void start() {
        millisecond = 0;
        startDate = new Date();
        currentDate = new Date();
        status = Status.RUNNING;
        timerThread.start();
    }

    public void stop() {
        status = Status.STOPPED;
    }

    public void pause() {
        timerThread = new TimerThread();
        status = Status.PAUSED;
    }

    public void unpause() {
        if (status == Status.PAUSED) {
            currentDate = new Date();
            timerThread.start();
            status = Status.RUNNING;
        }
    }

    public long getMillisecond() {
        return millisecond;
    }

    public static void main(String args[]) {
        final Timer timer = new Timer();
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(timer.getStatus() + "\t" + timer.getMillisecond());
                timer.start();
                System.out.println(timer.getStatus() + "\t" + timer.getMillisecond());
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    System.out.print("INTERRUPTED!");
                }
                timer.pause();
                System.out.println(timer.getStatus() + "\t" + timer.getMillisecond());
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("INTERRUPTED!");
                }
                timer.unpause();
                System.out.println(timer.getStatus() + "\t" + timer.getMillisecond());
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("INTERRUPTED!");
                }
                timer.stop();
                System.out.println(timer.getStatus() + "\t" + timer.getMillisecond());
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("INTERRUPTED!");
                }
                System.out.println(timer.getStatus() + "\t" + timer.getMillisecond());
            }
        };
        thread.start();
    }

    private String getStatus() {
        return status.msg;
    }

}
