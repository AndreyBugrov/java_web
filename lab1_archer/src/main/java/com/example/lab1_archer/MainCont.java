package com.example.lab1_archer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.TriangleMesh;

import java.io.Console;

public class MainCont {
    @FXML
    Circle big_target, small_target;
    @FXML
    Polygon arrow_tip;
    @FXML
    Line arrow_shaft;
    @FXML
    Label shoot_num;
    @FXML
    Label hit_num;
    Thread main_thread;
    int score;
    int shoots;
    boolean run_targets = true, run_arrow;
    double big_target_y, small_target_y, arrow_tip_x, arrow_shaft_x;

    @FXML
    protected void start_targets(){
        final double maxy = 182+62, miny=0;
        final double begin_arrow = 120;
        final double big_rad =26, small_rad = 13;
        final double arrow_y = 83;
        final double big_target_x = 404;
        final double small_target_x = 503;
        arrow_shaft_x = begin_arrow;
        arrow_tip_x= begin_arrow;
        big_target_y = (maxy-miny)/2;
        small_target_y = (maxy-miny)/2;
        score=0;
        shoots=0;
        change_shoot_num();
        change_hit_num();
        if(main_thread!=null) {
            return;
        }
        main_thread = new Thread(
                ()->{
                    arrow_tip.setLayoutY(arrow_y);
                    arrow_shaft.setLayoutY(123);
                    run_arrow=false;
                    while(run_targets)
                    {
                        arrow_tip.setLayoutX(arrow_tip_x);
                        arrow_shaft.setLayoutX(arrow_shaft_x);
                        big_target.setLayoutY(big_target_y);
                        small_target.setLayoutY(small_target_y);
                        big_target_y+=5;
                        small_target_y += 10;
                        if(run_arrow){
                            if(big_target.getLayoutY()-arrow_y<=2*big_rad&&big_target.getLayoutY()-arrow_y>=0
                                    &&arrow_shaft.getLayoutX()-big_target.getLayoutX()<=big_rad&&
                                    arrow_shaft.getLayoutX()-big_target.getLayoutX()>=13) {
                                score += 1;
                                arrow_shaft_x=begin_arrow;
                                arrow_tip_x=begin_arrow;
                                run_arrow=false;
                            }else
                                if(small_target.getLayoutY()-arrow_y<=2*small_rad&&small_target.getLayoutY()-arrow_y>=0
                                        &&arrow_shaft.getLayoutX()-small_target.getLayoutX()<=small_rad&&
                                        arrow_shaft.getLayoutX()-small_target.getLayoutX()>=0) {
//                                    System.out.println(arrow_shaft.getLayoutX());
//                                    System.out.println(small_target.getLayoutX());
                                    score += 2;
                                    arrow_shaft_x = begin_arrow;
                                    arrow_tip_x = begin_arrow;
                                    run_arrow = false;
                                }
                                else if(arrow_shaft_x>small_target.getLayoutX()+2*small_rad+13*3){
                                    arrow_shaft_x=begin_arrow;
                                    arrow_tip_x=begin_arrow;
                                    run_arrow=false;
                                    continue;
                                }
                            arrow_shaft_x += 13;
                            arrow_tip_x += 13;
                        }
                        if (big_target_y>maxy){
                            big_target_y=miny;
                        }
                        if(small_target_y>maxy){
                            small_target_y=miny;
                        }
                        try{
                            Thread.sleep(100);
                        }
                        catch (InterruptedException ex){
                            run_targets=false;}
                        Platform.runLater(() -> {
                            String s="Счёт: " + Integer.toString(score);
                            hit_num.setText(s);
                        });
                    }
                    arrow_shaft_x = begin_arrow;
                    arrow_tip_x= begin_arrow;
                    big_target_y = (maxy-miny)/2;
                    small_target_y = (maxy-miny)/2;

                    big_target.setLayoutY(big_target_y);
                    small_target.setLayoutY(small_target_y);
                    arrow_shaft.setLayoutX(arrow_shaft_x);
                    arrow_tip.setLayoutX(arrow_tip_x);
                }
        );
        main_thread.setDaemon(true);
        main_thread.start();
    }
    @FXML
    protected void shoot() {
        if(run_arrow) return;
        run_arrow=true;
        shoots+=1;
        change_shoot_num();
    }
    @FXML
    protected void stop(){
        if(main_thread==null) return;
        main_thread.interrupt();
        shoots=0;
        score=0;
        change_hit_num();
        change_shoot_num();
    }
    @FXML
    protected void change_shoot_num(){
        String s="Выстрелы: " + Integer.toString(shoots);
        shoot_num.setText(s);
    }
    @FXML
    protected void change_hit_num(){
        String s="Счёт: " + Integer.toString(score);
        hit_num.setText(s);
    }
}
