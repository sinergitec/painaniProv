package com.sienrgitec.painaniprov.model;

import java.io.Serializable;

public class opPedidoProveedor  implements Serializable {


    private Integer iPedido;
    private Integer iPedidoProv;
    private Integer iProveedor;
    private String dtFecha;
    private Integer iHora;
    private Integer iDomicilio;
    private Double deTotalPzas;
    private Double deSubTotal;
    private Double deImpuesto;
    private Double deImporte;
    private String dtAvisado;
    private Boolean lContestado;
    private String dtContestado;
    private Boolean lPagado;
    private Object dtPagado;
    private Boolean lVoBoPago;
    private Double deAporta;
    private Integer dePenalizacion;
    private Double dePagoNeto;
    private Boolean lCancelado;
    private Object dtCancelado;
    private Integer iRazon;
    private Boolean lEntregado;
    private String dtEntregado;
    private Object dtSurtido;
    private Integer dePesoTotal;
    private Integer deVolTotal;
    private String dtCreado;
    private String dtModificado;
    private String cUsuCrea;
    private String cUsuModifica;
    private Boolean lSurtido;
    private Object id;



    private static final long serialVersionUID = 1L;


    public Integer getiPedido() {
        return iPedido;
    }

    public void setiPedido(Integer iPedido) {
        this.iPedido = iPedido;
    }

    public Integer getiPedidoProv() {
        return iPedidoProv;
    }

    public void setiPedidoProv(Integer iPedidoProv) {
        this.iPedidoProv = iPedidoProv;
    }

    public Integer getiProveedor() {
        return iProveedor;
    }

    public void setiProveedor(Integer iProveedor) {
        this.iProveedor = iProveedor;
    }

    public String getDtFecha() {
        return dtFecha;
    }

    public void setDtFecha(String dtFecha) {
        this.dtFecha = dtFecha;
    }

    public Integer getiHora() {
        return iHora;
    }

    public void setiHora(Integer iHora) {
        this.iHora = iHora;
    }

    public Integer getiDomicilio() {
        return iDomicilio;
    }

    public void setiDomicilio(Integer iDomicilio) {
        this.iDomicilio = iDomicilio;
    }

    public Double getDeTotalPzas() {
        return deTotalPzas;
    }

    public void setDeTotalPzas(Double deTotalPzas) {
        this.deTotalPzas = deTotalPzas;
    }

    public Double getDeSubTotal() {
        return deSubTotal;
    }

    public void setDeSubTotal(Double deSubTotal) {
        this.deSubTotal = deSubTotal;
    }

    public Double getDeImpuesto() {
        return deImpuesto;
    }

    public void setDeImpuesto(Double deImpuesto) {
        this.deImpuesto = deImpuesto;
    }

    public Double getDeImporte() {
        return deImporte;
    }

    public void setDeImporte(Double deImporte) {
        this.deImporte = deImporte;
    }

    public String getDtAvisado() {
        return dtAvisado;
    }

    public void setDtAvisado(String dtAvisado) {
        this.dtAvisado = dtAvisado;
    }

    public Boolean getlContestado() {
        return lContestado;
    }

    public void setlContestado(Boolean lContestado) {
        this.lContestado = lContestado;
    }

    public String getDtContestado() {
        return dtContestado;
    }

    public void setDtContestado(String dtContestado) {
        this.dtContestado = dtContestado;
    }

    public Boolean getlPagado() {
        return lPagado;
    }

    public void setlPagado(Boolean lPagado) {
        this.lPagado = lPagado;
    }

    public Object getDtPagado() {
        return dtPagado;
    }

    public void setDtPagado(Object dtPagado) {
        this.dtPagado = dtPagado;
    }

    public Boolean getlVoBoPago() {
        return lVoBoPago;
    }

    public void setlVoBoPago(Boolean lVoBoPago) {
        this.lVoBoPago = lVoBoPago;
    }

    public Double getDeAporta() {
        return deAporta;
    }

    public void setDeAporta(Double deAporta) {
        this.deAporta = deAporta;
    }

    public Integer getDePenalizacion() {
        return dePenalizacion;
    }

    public void setDePenalizacion(Integer dePenalizacion) {
        this.dePenalizacion = dePenalizacion;
    }

    public Double getDePagoNeto() {
        return dePagoNeto;
    }

    public void setDePagoNeto(Double dePagoNeto) {
        this.dePagoNeto = dePagoNeto;
    }

    public Boolean getlCancelado() {
        return lCancelado;
    }

    public void setlCancelado(Boolean lCancelado) {
        this.lCancelado = lCancelado;
    }

    public Object getDtCancelado() {
        return dtCancelado;
    }

    public void setDtCancelado(Object dtCancelado) {
        this.dtCancelado = dtCancelado;
    }

    public Integer getiRazon() {
        return iRazon;
    }

    public void setiRazon(Integer iRazon) {
        this.iRazon = iRazon;
    }

    public Boolean getlEntregado() {
        return lEntregado;
    }

    public void setlEntregado(Boolean lEntregado) {
        this.lEntregado = lEntregado;
    }

    public String getDtEntregado() {
        return dtEntregado;
    }

    public void setDtEntregado(String dtEntregado) {
        this.dtEntregado = dtEntregado;
    }

    public Object getDtSurtido() {
        return dtSurtido;
    }

    public void setDtSurtido(Object dtSurtido) {
        this.dtSurtido = dtSurtido;
    }

    public Integer getDePesoTotal() {
        return dePesoTotal;
    }

    public void setDePesoTotal(Integer dePesoTotal) {
        this.dePesoTotal = dePesoTotal;
    }

    public Integer getDeVolTotal() {
        return deVolTotal;
    }

    public void setDeVolTotal(Integer deVolTotal) {
        this.deVolTotal = deVolTotal;
    }

    public String getDtCreado() {
        return dtCreado;
    }

    public void setDtCreado(String dtCreado) {
        this.dtCreado = dtCreado;
    }

    public String getDtModificado() {
        return dtModificado;
    }

    public void setDtModificado(String dtModificado) {
        this.dtModificado = dtModificado;
    }

    public String getcUsuCrea() {
        return cUsuCrea;
    }

    public void setcUsuCrea(String cUsuCrea) {
        this.cUsuCrea = cUsuCrea;
    }

    public String getcUsuModifica() {
        return cUsuModifica;
    }

    public void setcUsuModifica(String cUsuModifica) {
        this.cUsuModifica = cUsuModifica;
    }

    public Boolean getlSurtido() {
        return lSurtido;
    }

    public void setlSurtido(Boolean lSurtido) {
        this.lSurtido = lSurtido;
    }

    public Object getId() {
        return id;
    }


    public void setId(Object id) {
        this.id = id;
    }
}
