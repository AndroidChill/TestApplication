package com.example.testapplication.app;

import android.app.Activity;

import java.util.Stack;

public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager(){

    }

    public static AppManager getInstance() {
        if(instance == null){
            instance = new AppManager();
        }
        return instance;
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public void addActivity(Activity activity){
        if (activityStack == null){
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity){
        if(activity != null){
            activityStack.remove(activity);
        }
    }

    public boolean isActivity(){
        if(activityStack != null){
            return !activityStack.isEmpty();
        }
        return false;
    }

    public Activity currentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity(){
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity){
        if(activity != null){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

    public void finishActivity(Class<?> cls){
        for(Activity activity : activityStack){
            if(activity.getClass().equals(cls)){
                finishActivity(activity);
                break;
            }
        }
    }

    public void finishAllActivity(){
        for(int i = 0, size = activityStack.size(); i < size; i++){
            if(activityStack.get(i) != null){
                finishActivity(activityStack.get(i));
            }
        }
        activityStack.clear();
    }

    public Activity getActivity(Class<?> cls){
        if(activityStack != null){
            for(Activity activity : activityStack){
                if(activity.getClass().equals(cls)){
                    return activity;
                }
            }
        }
        return null;
    }

    public void AppExit(){
        try{
            finishAllActivity();
        } catch (Exception e){
            activityStack.clear();
            e.printStackTrace();
        }
    }
}
