package com.sienrgitec.painaniprov.model;

import java.io.Serializable;

public class opPagosProveedorDet implements Serializable {
    private Object id;
    private Integer iProveedor;
    private Integer iPago;
    private Integer iPedido;
    private Double deImporte;
    private Double dePenalizacion;
    private Boolean lCancelado;
    private Double deCancelacion;
    private Double dePagoBruto;
    private Double deAportacionCP;
    private Double dePropinaT;
    private Double dePagoNeto;
    private String dtCreado;
    private String dtModifcado;
    private String cUsuCrea;
    private String cUsuModifca;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Integer getiProveedor() {
        return iProveedor;
    }

    public void setiProveedor(Integer iProveedor) {
        this.iProveedor = iProveedor;
    }

    public Integer getiPago() {
        return iPago;
    }

    public void setiPago(Integer iPago) {
        this.iPago = iPago;
    }

    public Integer getiPedido() {
        return iPedido;
    }

    public void setiPedido(Integer iPedido) {
        this.iPedido = iPedido;
    }

    public Double getDeImporte() {
        return deImporte;
    }

    public void setDeImporte(Double deImporte) {
        this.deImporte = deImporte;
    }

    public Double getDePenalizacion() {
        return dePenalizacion;
    }

    public void setDePenalizacion(Double dePenalizacion) {
        this.dePenalizacion = dePenalizacion;
    }

    public Boolean getlCancelado() {
        return lCancelado;
    }

    public void setlCancelado(Boolean lCancelado) {
        this.lCancelado = lCancelado;
    }

    public Double getDeCancelacion() {
        return deCancelacion;
    }

    public void setDeCancelacion(Double deCancelacion) {
        this.deCancelacion = deCancelacion;
    }

    public Double getDePagoBruto() {
        return dePagoBruto;
    }

    public void setDePagoBruto(Double dePagoBruto) {
        this.dePagoBruto = dePagoBruto;
    }

    public Double getDeAportacionCP() {
        return deAportacionCP;
    }

    public void setDeAportacionCP(Double deAportacionCP) {
        this.deAportacionCP = deAportacionCP;
    }

    public Double getDePropinaT() {
        return dePropinaT;
    }

    public void setDePropinaT(Double dePropinaT) {
        this.dePropinaT = dePropinaT;
    }

    public Double getDePagoNeto() {
        return dePagoNeto;
    }

    public void setDePagoNeto(Double dePagoNeto) {
        this.dePagoNeto = dePagoNeto;
    }

    public String getDtCreado() {
        return dtCreado;
    }

    public void setDtCreado(String dtCreado) {
        this.dtCreado = dtCreado;
    }

    public String getDtModifcado() {
        return dtModifcado;
    }

    public void setDtModifcado(String dtModifcado) {
        this.dtModifcado = dtModifcado;
    }

    public String getcUsuCrea() {
        return cUsuCrea;
    }

    public void setcUsuCrea(String cUsuCrea) {
        this.cUsuCrea = cUsuCrea;
    }

    public String getcUsuModifca() {
        return cUsuModifca;
    }

    public void setcUsuModifca(String cUsuModifca) {
        this.cUsuModifca = cUsuModifca;
    }
}
