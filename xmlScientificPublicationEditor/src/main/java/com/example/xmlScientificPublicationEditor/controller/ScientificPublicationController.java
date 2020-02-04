package com.example.xmlScientificPublicationEditor.controller;

import java.io.ByteArrayOutputStream;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.xmlScientificPublicationEditor.service.ScientificPublicationService;
import com.example.xmlScientificPublicationEditor.util.MyFile;

@RestController
@RequestMapping("/api")
public class ScientificPublicationController extends BaseController {

	@Autowired
	private ScientificPublicationService scientificPublicationService;

	@GetMapping(value = "/scientificPublication/process/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getScientificPublicationByProcessId(@PathVariable("id") String id) throws Exception {
		String sp = scientificPublicationService.findOneByProcessId(id, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}

	@GetMapping(value = "/scientificPublication/getMetadataXml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getMetadataSP(@PathVariable("id") String id) throws Exception {
		String metadata = scientificPublicationService.getMetadataSPXML(id);
		return new ResponseEntity<>(metadata, HttpStatus.OK);
	}

	@GetMapping(value = "/scientificPublication/getMetadataJSON/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getMetadataSPJSON(@PathVariable("id") String id) throws Exception {
		String metadata = scientificPublicationService.getMetadataSPJSON(id);
		return new ResponseEntity<>(metadata, HttpStatus.OK);
	}

	
	@GetMapping(value = "/scientificPublication/process/html/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getScientificPublicationByProcessIdHTML(@PathVariable("id") String id) throws Exception {
		String sp = scientificPublicationService.findOneByProcessIdHTML(id, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
	
	@GetMapping(value = "/scientificPublication/process/pdf/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<byte[]> getScientificPublicationByProcessIdPDF(@PathVariable("id") String id) throws Exception {
		ByteArrayOutputStream sp = scientificPublicationService.findOneByProcessIdPDF(id, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return new ResponseEntity<>(sp.toByteArray(), HttpStatus.OK);
	}
	

	@GetMapping(value = "/scientificPublication/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> getScientificPublicationById(@PathVariable("id") String id) throws Exception {
		String sp = scientificPublicationService.findOnePub(id);
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
	
	@GetMapping(value = "/scientificPublication/html/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> getScientificPublicationByIdHTML(@PathVariable("id") String id) throws Exception {
		String sp = scientificPublicationService.findOneHTML(id);
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}

	@GetMapping(value = "/scientificPublication/pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getScientificPublicationByIdPDF(@PathVariable("id") String id) throws Exception {
		ByteArrayOutputStream sp = scientificPublicationService.findOnePDF(id);
		return new ResponseEntity<>(sp.toByteArray(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/scientificPublication/version/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getScientificPublicationByVersion(@PathVariable("id") String id, Principal user) throws Exception {
		String sp = scientificPublicationService.findOneByVersion(id, user.getName());
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
	
	@GetMapping(value = "/scientificPublication/version/html/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getScientificPublicationByVersionHTML(@PathVariable("id") String id, Principal user) throws Exception {
		String sp = scientificPublicationService.findOneByVersionHTML(id, user.getName());
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
	
	@GetMapping(value = "/scientificPublication/version/pdf/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<byte[]> getScientificPublicationByVersionPDF(@PathVariable("id") String id, Principal user) throws Exception {
		ByteArrayOutputStream sp = scientificPublicationService.findOneByVersionPDF(id, user.getName());
		return new ResponseEntity<>(sp.toByteArray(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/scientificPublication/notPub/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getScientificPublicationNotPubById(@PathVariable("id") String id, Principal user) throws Exception {
		String sp = scientificPublicationService.findOneNotPub(id, user.getName());
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}

	@GetMapping(value = "/scientificPublication/review/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> reviewSP(@PathVariable("id") String processId, Principal user) throws Exception {
		String result = scientificPublicationService.getSPReview(processId, user.getName());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/scientificPublication/review/html/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> reviewSPHTML(@PathVariable("id") String processId, Principal user) throws Exception {
		String result = scientificPublicationService.getSPReviewHTML(processId, user.getName());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/scientificPublication/review/pdf/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<byte[]> reviewSPPDF(@PathVariable("id") String processId, Principal user) throws Exception {
		ByteArrayOutputStream sp = scientificPublicationService.getSPReviewPDF(processId, user.getName());
		return new ResponseEntity<>(sp.toByteArray(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/scientificPublication", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> addScientificPublication(@RequestBody String scientificPublication, Principal author)
			throws Exception {
		String id = scientificPublicationService.save(scientificPublication, author.getName());
		return new ResponseEntity<>(String.format("You succesfully add scientific publication with id %s", id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/scientificPublication/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> addScientificPublicationNewVersion(@PathVariable("id") String processId,
			@RequestBody String scientificPublication, Principal author) throws Exception {
		String id = scientificPublicationService.saveNewVersion(scientificPublication, author.getName(), processId);
		return new ResponseEntity<>(String.format("You succesfully add scientific publication with id %s", id),
				HttpStatus.OK);
	}
	
	@PostMapping(value = "/scientificPublication/review/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> addReviewComments(@PathVariable("id") String processId,
			@RequestBody String scientificPublication, Principal author) throws Exception {
		scientificPublicationService.saveComments(scientificPublication, author.getName(), processId);
		return new ResponseEntity<>("You succesfully add comments for scientific publication", HttpStatus.OK);
	}
	
	@PostMapping(value="/scientificPublication/upload", 
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> uploadScientificPublication(
			@RequestParam(("file")) MultipartFile q, Principal author) throws Exception {
		String file = MyFile.readFile(q);
		String id = scientificPublicationService.save(file, author.getName());
		return new ResponseEntity<>(String.format("You succesfully add scientific publication with id %s",id), HttpStatus.OK);
	}
	
	@PostMapping(value = "/scientificPublication/upload/newVersion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> uploadNewVersion(@RequestParam(("processId")) String processId,
			@RequestParam(("file")) MultipartFile q, Principal author) throws Exception {
		String file = MyFile.readFile(q);
		String id = scientificPublicationService.saveNewVersion(file, author.getName(), processId);
		return new ResponseEntity<>(String.format("You succesfully add scientific publication with id %s", id),
				HttpStatus.OK);
	}
	
	@PostMapping(value = "/scientificPublication/upload/review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> uploadReviewComments(@RequestParam(("processId")) String processId,
			@RequestParam(("file")) MultipartFile q, Principal author) throws Exception {
		String file = MyFile.readFile(q);
		scientificPublicationService.saveComments(file, author.getName(), processId);
		return new ResponseEntity<>("You succesfully add comments for scientific publication", HttpStatus.OK);
	}
	
	@PutMapping(value="/scientificPublication", consumes = MediaType.APPLICATION_XML_VALUE,produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> updateScientificPublication(@RequestBody String scientificPublication) throws Exception {
		String sp = scientificPublicationService.update(scientificPublication);
		return new ResponseEntity<>(sp, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/scientificPublication/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> deleteScientificPublication(@PathVariable("id")String id) throws Exception{
		scientificPublicationService.delete(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/scientificPublication/getSPTemplate", produces = MediaType.APPLICATION_XML_VALUE)
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_REDACTOR')")
	public ResponseEntity<String> getSPTemplate() throws Exception {
		String sp = scientificPublicationService.generateSPXMLTemplate();
        return new ResponseEntity<>(sp, HttpStatus.OK);
	}
	
	@GetMapping(value = "/scientificPublication/search", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> search(@RequestParam(("param")) String param,Principal user) throws Exception {
		String result = scientificPublicationService.search(param, user);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}

    @GetMapping(value = "/scientificPublication/searchMetadata", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> searchMetadata(@RequestParam(("param")) String param, Principal user) throws Exception {
        String result = scientificPublicationService.metadataSearch(param, user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
