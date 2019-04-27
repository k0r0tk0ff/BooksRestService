package ru.k0r0tk0ff.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

/**
 * Created by korotkov_a_a on 01.11.2018.
 */
public interface EntityController {
    ResponseEntity<?> getEntityById(@PathVariable("id") Long id);
    ResponseEntity<?> delEntityById(@PathVariable("id") Long id);
    ResponseEntity<?> getAllEntities();
    ResponseEntity<?> addEntity(@RequestBody Map<String, String> parameters);
    ResponseEntity<?> updateEntity(@RequestBody Map<String, String> parameters);
}
