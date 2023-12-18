package com.company.springframework.controller;

import com.company.springframework.model.Empleado;
import com.company.springframework.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> listarTodosLosEmpleados() {
        return empleadoService.listarTodosLosEmpleados();
    }

    @GetMapping("/{id}")
    public Empleado obtenerEmpleado(@PathVariable("id") Long id) {
        return empleadoService.obtenerEmpleado(id);
    }

    @PostMapping
    public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.guardarEmpleado(empleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable("id") Long id, @RequestBody Empleado empleado) {
        empleado.setId(id);
        return empleadoService.actualizarEmpleado(empleado);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable("id") Long id) {
        empleadoService.eliminarEmpleado(id);
    }
}
@GetMapping("/reporte02")
public ResponseEntity<byte[]> visualizarReporte02() throws JRException {

    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(empleadoService.listarTodosLosEmpleados());
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("titulo", "Reporte de Empleados");
    JasperPrint jasperPrint = JasperFillManager.fillReport("src/main/resources/reportes/reporte02.jasper", parameters, dataSource);

    byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_PDF);
    headers.setContentDisposition(ContentDisposition.inline().filename("empleados.pdf").build());

    return ResponseEntity.ok()
            .headers(headers)
            .body(reporte);

}

