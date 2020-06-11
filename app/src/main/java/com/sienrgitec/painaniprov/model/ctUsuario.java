package com.sienrgitec.painaniprov.model;

import java.io.Serializable;

public class ctUsuario  implements Serializable {

    private String cUsuario;
    private String cPassword;
    private Integer iPersona;
    private Integer iTipoPersona;
    private Integer iDomicilio;
    private Boolean lActivo;
    private String dtCreado;
    private String dtModificado;
    private String cUsuCrea;
    private String cUsuModifica;
    private Object id;

    public String getcUsuario() {
        return cUsuario;
    }

    public void setcUsuario(String cUsuario) {
        this.cUsuario = cUsuario;
    }

    public String getcPassword() {
        return cPassword;
    }

    public void setcPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    public Integer getiPersona() {
        return iPersona;
    }

    public void setiPersona(Integer iPersona) {
        this.iPersona = iPersona;
    }

    public Integer getiTipoPersona() {
        return iTipoPersona;
    }

    public void setiTipoPersona(Integer iTipoPersona) {
        this.iTipoPersona = iTipoPersona;
    }

    public Integer getiDomicilio() {
        return iDomicilio;
    }

    public void setiDomicilio(Integer iDomicilio) {
        this.iDomicilio = iDomicilio;
    }

    public Boolean getlActivo() {
        return lActivo;
    }

    public void setlActivo(Boolean lActivo) {
        this.lActivo = lActivo;
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

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }
}
