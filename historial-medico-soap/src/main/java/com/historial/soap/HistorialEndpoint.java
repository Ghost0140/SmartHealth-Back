package com.historial.soap;

import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.historial.historial.CrearHistorialRequest;
import com.historial.historial.CrearHistorialResponse;
import com.historial.historial.HistorialType;
import com.historial.historial.ListarHistorialesPorPacienteRequest;
import com.historial.historial.ListarHistorialesPorPacienteResponse;
import com.historial.historial.ListarHistorialesRequest;
import com.historial.historial.ListarHistorialesResponse;
import com.historial.historial.ObtenerHistorialRequest;
import com.historial.historial.ObtenerHistorialResponse;
import com.historial.model.HistorialClinico;
import com.historial.service.HistorialMedicoService;

@Endpoint
public class HistorialEndpoint {

    private static final String NAMESPACE_URI = "http://www.historial.com/historial";

    private static final DatatypeFactory DF;

    static {
        try {
            DF = DatatypeFactory.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private HistorialMedicoService servicio;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "crearHistorialRequest")
    @ResponsePayload
    public CrearHistorialResponse crear(@RequestPayload CrearHistorialRequest request) {

        HistorialClinico h = new HistorialClinico();

        h.setIdPaciente(request.getPacienteId());
        h.setIdDoctor(request.getDoctorId());
        h.setDiagnostico(request.getDiagnostico());
        h.setTratamiento(request.getTratamiento());
        h.setObservacion(request.getObservacion());
        h.setFechaAtencion(
                request.getFechaAtencion()
                        .toGregorianCalendar()
                        .toZonedDateTime()
                        .toLocalDate()
        );

        servicio.registrarHistorial(h);

        CrearHistorialResponse response = new CrearHistorialResponse();
        response.setMensaje("Historial clínico registrado correctamente");

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerHistorialRequest")
    @ResponsePayload
    public ObtenerHistorialResponse obtener(@RequestPayload ObtenerHistorialRequest request) {

        HistorialClinico h = servicio.obtenerPorId(request.getId());

        ObtenerHistorialResponse response = new ObtenerHistorialResponse();

        if (h != null) {
            response.setHistorial(convertirAHistorialType(h));
            response.setMensaje("Historial encontrado");
        } else {
            response.setMensaje("No se encontró historial con ID: " + request.getId());
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarHistorialesRequest")
    @ResponsePayload
    public ListarHistorialesResponse listar(@RequestPayload ListarHistorialesRequest request) {

        List<HistorialClinico> lista = servicio.listarHistorial();

        ListarHistorialesResponse response = new ListarHistorialesResponse();

        for (HistorialClinico h : lista) {
            response.getHistorial().add(convertirAHistorialType(h));
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "listarHistorialesPorPacienteRequest")
    @ResponsePayload
    public ListarHistorialesPorPacienteResponse listarPorPaciente(
            @RequestPayload ListarHistorialesPorPacienteRequest request) {

        List<HistorialClinico> lista = servicio.listarPorPaciente(request.getPacienteId());

        ListarHistorialesPorPacienteResponse response =
                new ListarHistorialesPorPacienteResponse();

        for (HistorialClinico h : lista) {
            response.getHistorial().add(convertirAHistorialType(h));
        }

        return response;
    }

    private HistorialType convertirAHistorialType(HistorialClinico h) {

        HistorialType t = new HistorialType();

        t.setId(h.getId());
        t.setPacienteId(h.getIdPaciente());
        t.setDoctorId(h.getIdDoctor());
        t.setDiagnostico(h.getDiagnostico());
        t.setTratamiento(h.getTratamiento());
        t.setObservacion(h.getObservacion());

        t.setFechaAtencion(
                DF.newXMLGregorianCalendar(
                        GregorianCalendar.from(
                                h.getFechaAtencion()
                                        .atStartOfDay(ZoneId.systemDefault())
                        )
                )
        );

        return t;
    }
}