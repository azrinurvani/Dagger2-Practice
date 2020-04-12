package com.mobile.azrinurvani.dagger2practice.di.auth;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*Custom Scope
* Fungsi dari custom scope ini adalah untuk menghapus seluruh depedency (clear depedency) pada dagger
* apabila activity destroyed, dan membuat ulang apabila dijalankan kembali
* Hal itu berfungsi untuk mengurangi penggunaan memory
* Dan custom scope juga berfungsi untuk memberi pembatasan akses dari module yang ada terhadap activity atau class2 lain*/
@Scope
@Documented
@Retention(RUNTIME)
public @interface AuthScope {
}
