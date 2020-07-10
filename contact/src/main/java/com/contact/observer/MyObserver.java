/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.contact.observer;

public abstract class MyObserver {

    protected MySubjectObserver mySubjectObserver;

    public abstract void updateSource();
}
