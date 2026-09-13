//
// Este archivo ha sido generado por Eclipse Implementation of JAXB v3.0.0 
// Visite https://eclipse-ee4j.github.io/jaxb-ri 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2026.06.20 a las 10:45:44 AM PET 
//


package com.historial.historial;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="pacienteId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="doctorId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="diagnostico" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="tratamiento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="observacion" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fechaAtencion" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "id",
    "pacienteId",
    "doctorId",
    "diagnostico",
    "tratamiento",
    "observacion",
    "fechaAtencion"
})
@XmlRootElement(name = "actualizarHistorialRequest")
public class ActualizarHistorialRequest {

    protected int id;
    protected int pacienteId;
    protected int doctorId;
    @XmlElement(required = true)
    protected String diagnostico;
    @XmlElement(required = true)
    protected String tratamiento;
    @XmlElement(required = true)
    protected String observacion;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaAtencion;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad pacienteId.
     * 
     */
    public int getPacienteId() {
        return pacienteId;
    }

    /**
     * Define el valor de la propiedad pacienteId.
     * 
     */
    public void setPacienteId(int value) {
        this.pacienteId = value;
    }

    /**
     * Obtiene el valor de la propiedad doctorId.
     * 
     */
    public int getDoctorId() {
        return doctorId;
    }

    /**
     * Define el valor de la propiedad doctorId.
     * 
     */
    public void setDoctorId(int value) {
        this.doctorId = value;
    }

    /**
     * Obtiene el valor de la propiedad diagnostico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * Define el valor de la propiedad diagnostico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnostico(String value) {
        this.diagnostico = value;
    }

    /**
     * Obtiene el valor de la propiedad tratamiento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTratamiento() {
        return tratamiento;
    }

    /**
     * Define el valor de la propiedad tratamiento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTratamiento(String value) {
        this.tratamiento = value;
    }

    /**
     * Obtiene el valor de la propiedad observacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Define el valor de la propiedad observacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservacion(String value) {
        this.observacion = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaAtencion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaAtencion() {
        return fechaAtencion;
    }

    /**
     * Define el valor de la propiedad fechaAtencion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaAtencion(XMLGregorianCalendar value) {
        this.fechaAtencion = value;
    }

}
