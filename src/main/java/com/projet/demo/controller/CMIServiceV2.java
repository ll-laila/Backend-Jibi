package com.projet.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@PreAuthorize("hasAuthority('AGENT')")
@RequestMapping("/fim/est3DgateV2")
public class CMIServiceV2 {

}
