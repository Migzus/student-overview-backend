package com.migzus.api.student_overview.controllers;

import com.migzus.api.student_overview.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

// this is not actually a very advanced version of the ControllerTemplate class...
// I had no idea what else to call this lmao.

// regardless this is supposed to mostly give you the flexibility to specify what we receive in the request's bodies
// methods "create" and "update" are marked as abstract to force you to override those methods.
// seeing as here we need special logic to accommodate and parse the body appropriately
public abstract class AdvancedControllerTemplate<M extends Model, N> {
    @Autowired
    protected JpaRepository<M, Integer> repository;

    @GetMapping
    public List<M> getAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<M> getById(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }

    @PostMapping
    public abstract ResponseEntity<M> create(@RequestBody N request);

    @PutMapping("{id}")
    public abstract ResponseEntity<M> update(@PathVariable final Integer id, @RequestBody final N request);

    // this method should... uhhh not be used... will cause null references here and there
    @DeleteMapping("{id}")
    public ResponseEntity<M> remove(@PathVariable final Integer id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found.")), HttpStatus.OK);
    }
}
