package com.sienrgitec.painaniprov.config;

import com.sienrgitec.painaniprov.model.ctCategoriaProv;
import com.sienrgitec.painaniprov.model.ctMarca;
import com.sienrgitec.painaniprov.model.ctProveedor;
import com.sienrgitec.painaniprov.model.ctSubCategoriaProv;
import com.sienrgitec.painaniprov.model.ctUsuario;

import java.util.List;

public class Globales {

   public static  String  URL = "http://192.168.1.102:8083/painal/rest/painalService/";

    //public static String URL = "http://189.188.71.150:8083/painal/rest/painalService/";

    public static ctUsuario g_ctUsuario = null;
    public static ctProveedor g_ctProveedor = null;
    public static List<ctMarca> g_ctMarca = null;

    public static List<ctCategoriaProv> g_ctCategoriaProv = null;
    public static List<ctSubCategoriaProv> g_ctSubCategoriaProv = null;


}
