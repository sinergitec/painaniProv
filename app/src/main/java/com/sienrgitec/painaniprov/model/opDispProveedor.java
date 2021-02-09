package com.sienrgitec.painaniprov.model;

public class opDispProveedor {
    private Integer iProveedor;
    private Integer iUnidad;
    private Object dtFecha;
    private Integer iDomicilio;
    private Integer iComision;
    private Double  deComision;
    private Double dePropina;

    private Object dtCheckIn;
    private Integer iCheckIn;
    private Object dtCheckOut;
    private Integer iCheckOut;
    private Object id;

    public Integer getiProveedor() {
        return iProveedor;
    }

    public void setiProveedor(Integer iProveedor) {
        this.iProveedor = iProveedor;
    }

    public Integer getiUnidad() {
        return iUnidad;
    }

    public void setiUnidad(Integer iUnidad) {
        this.iUnidad = iUnidad;
    }

    public Object getDtFecha() {
        return dtFecha;
    }

    public void setDtFecha(Object dtFecha) {
        this.dtFecha = dtFecha;
    }

    public Integer getiDomicilio() {
        return iDomicilio;
    }

    public void setiDomicilio(Integer iDomicilio) {
        this.iDomicilio = iDomicilio;
    }

    public Integer getiComision() {
        return iComision;
    }

    public void setiComision(Integer iComision) {
        this.iComision = iComision;
    }

    public Double getDePropina() {
        return dePropina;
    }

    public void setDePropina(Double dePropina) {
        this.dePropina = dePropina;
    }

    public Object getDtCheckIn() {
        return dtCheckIn;
    }

    public void setDtCheckIn(Object dtCheckIn) {
        this.dtCheckIn = dtCheckIn;
    }

    public Integer getiCheckIn() {
        return iCheckIn;
    }

    public void setiCheckIn(Integer iCheckIn) {
        this.iCheckIn = iCheckIn;
    }

    public Object getDtCheckOut() {
        return dtCheckOut;
    }

    public void setDtCheckOut(Object dtCheckOut) {
        this.dtCheckOut = dtCheckOut;
    }

    public Integer getiCheckOut() {
        return iCheckOut;
    }

    public void setiCheckOut(Integer iCheckOut) {
        this.iCheckOut = iCheckOut;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Double getDeComision() {
        return deComision;
    }

    public void setDeComision(Double deComision) {
        this.deComision = deComision;
    }
}
