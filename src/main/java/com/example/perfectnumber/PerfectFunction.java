package com.example.perfectnumber;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class PerfectFunction {

    private final int num;
    private final int cnt;
    private final List<Thread> threads;
    private final List<ProgressIndicator> progressIndicator;
    private final TextArea resultArea;
    private final HBox progressBox;
    private int counterLess = 0;
    private int counterMore = 0;

    private class PerfectNumberTask implements Runnable {

        private final int start;
        private final int end;

        public PerfectNumberTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i <= end; i++) {
                if (Checker(i) == 1) {
                    int isPerfect = i;
                    Platform.runLater(() -> resultArea.appendText(isPerfect + " is a perfect number.\n"));
                }else if(Checker(i) == 0)
                    counterLess++;
                else
                    counterMore++;

                double progress = ((double) (i - start + 1) / (end - start + 1));
                updateProgress(progress);
            }

        }

        private void updateProgress(double progress) {
            int index = threads.indexOf(Thread.currentThread());
            Platform.runLater(() -> progressIndicator.get(index).setProgress(progress));
        }
    }

    public PerfectFunction(int num, int cnt, TextArea resultArea, HBox progressBox) {
        this.num = num;
        this.cnt = cnt;
        this.threads = new ArrayList<>();
        this.progressIndicator = new ArrayList<>();
        this.resultArea = resultArea;
        this.progressBox = progressBox;
    }

    public void findPerfectNumbers() {
        int chunk = num / cnt;
        int start = 1;
        for (int i = 0; i < cnt; i++) {
            int end = (i == cnt - 1) ? num : start + chunk;
            Thread thread = new Thread(new PerfectNumberTask(start, end));
            threads.add(thread);
            ProgressIndicator CircleProgress = new ProgressIndicator(0);
            CircleProgress.setPadding(new Insets(0, 50, 0 , 0) );
            progressIndicator.add(CircleProgress);
            Platform.runLater(() -> progressBox.getChildren().add(CircleProgress));
            start = end + 1;
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized (this){
            Platform.runLater(() ->resultArea.appendText("There were "+ counterLess +" that were LESS Perfect Numbers!\nThere were "+ counterMore +" that were MORE Perfect Numbers!"));
        }
    }

    private int Checker(int n) {
        if(Factors(n) == n)
            return 1;
        else if(Factors(n) < n){
            return 0;
        }
        else
            return 2;

    }

    private int Factors(int n) {
        int sum = 0;
        for (int i = 1; i <= n / 2; i++) {
            if (n % i == 0) {
                sum += i;
            }
        }
        return sum;
    }


}