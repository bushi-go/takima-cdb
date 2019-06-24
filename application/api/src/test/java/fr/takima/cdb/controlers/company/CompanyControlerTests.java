package fr.takima.cdb.controlers.company;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fr.takima.cdb.config.HypermediaConfiguration;
import fr.takima.cdb.model.company.Company;
import fr.takima.cdb.model.company.CompanyRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(CompanyControler.class)
@Import({ HypermediaConfiguration.class })
public class CompanyControlerTests {

	@Autowired private MockMvc mvc;

	@MockBean private CompanyRepository repository;

	@Test
	public void getAllShouldFetchAHalFormsEmbeddedDocument() throws Exception {

		given(repository.findAll()).willReturn(Arrays.asList( //
				new Company(1L, "Microsoft"), //
				new Company(2L, "Takima")));

		mvc.perform(get("/companies").accept(MediaTypes.HAL_FORMS_JSON_VALUE)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_FORMS_JSON_VALUE))

				.andExpect(jsonPath("$._embedded.companies[0].cpyId", is(1)))
				.andExpect(jsonPath("$._embedded.companies[0].name", is("Microsoft")))
				.andExpect(jsonPath("$._embedded.companies[0]._templates.default.method", is("put")))
				.andExpect(jsonPath("$._embedded.companies[0]._templates.default.properties[0].name", is("name")))
				.andExpect(jsonPath("$._embedded.companies[0]._templates.default.properties[0].required", is(true)))
				.andExpect(jsonPath("$._embedded.companies[0]._templates.default.properties[1].name", is("id")))
				.andExpect(jsonPath("$._embedded.companies[0]._templates.default.properties[1].required", is(true)))
				.andExpect(jsonPath("$._embedded.companies[0]._templates.default.properties[2].name", is("lastName")))
				.andExpect(jsonPath("$._embedded.companies[0]._templates.default.properties[2].required", is(true)))
				.andExpect(jsonPath("$._embedded.companies[0]._templates.default.properties[3].name", is("role")))
				.andExpect(jsonPath("$._embedded.companies[0]._templates.default.properties[3].required", is(true)))
				.andExpect(jsonPath("$._embedded.companies[0]._links.self.href", is("http://localhost:8080/companies/1")))
				.andExpect(jsonPath("$._embedded.companies[0]._links.companies.href", is("http://localhost:8080/companies")))

				.andExpect(jsonPath("$._embedded.companies[1].id", is(2)))
				.andExpect(jsonPath("$._embedded.companies[1].firstName", is("Bilbo")))
				.andExpect(jsonPath("$._embedded.companies[1].lastName", is("Baggins")))
				.andExpect(jsonPath("$._embedded.companies[1].role", is("burglar")))
				.andExpect(jsonPath("$._embedded.companies[1]._templates.default.method", is("put")))
				.andExpect(jsonPath("$._embedded.companies[1]._templates.default.properties[0].name", is("firstName")))
				.andExpect(jsonPath("$._embedded.companies[1]._templates.default.properties[0].required", is(true)))
				.andExpect(jsonPath("$._embedded.companies[1]._templates.default.properties[1].name", is("id")))
				.andExpect(jsonPath("$._embedded.companies[1]._templates.default.properties[1].required", is(true)))
				.andExpect(jsonPath("$._embedded.companies[1]._templates.default.properties[2].name", is("lastName")))
				.andExpect(jsonPath("$._embedded.companies[1]._templates.default.properties[2].required", is(true)))
				.andExpect(jsonPath("$._embedded.companies[1]._templates.default.properties[3].name", is("role")))
				.andExpect(jsonPath("$._embedded.companies[1]._templates.default.properties[3].required", is(true)))
				.andExpect(jsonPath("$._embedded.companies[1]._links.self.href", is("http://localhost/companies/2")))
				.andExpect(jsonPath("$._embedded.companies[1]._links.companies.href", is("http://localhost/companies")))

				.andExpect(jsonPath("$._templates.default.method", is("post")))
				.andExpect(jsonPath("$._templates.default.properties[0].name", is("firstName")))
				.andExpect(jsonPath("$._templates.default.properties[0].required", is(true)))
				.andExpect(jsonPath("$._templates.default.properties[1].name", is("id")))
				.andExpect(jsonPath("$._templates.default.properties[1].required", is(true)))
				.andExpect(jsonPath("$._templates.default.properties[2].name", is("lastName")))
				.andExpect(jsonPath("$._templates.default.properties[2].required", is(true)))
				.andExpect(jsonPath("$._templates.default.properties[3].name", is("role")))
				.andExpect(jsonPath("$._templates.default.properties[3].required", is(true)))

				.andExpect(jsonPath("$._links.self.href", is("http://localhost:8080/companies")));
	}

	@Test
	public void getOneShouldFetchASingleHalFormsDocument() throws Exception {

		given(repository.findById(any())).willReturn(Optional.of(new Company(1L, "Apple")));

		mvc.perform(get("/companies/1").accept(MediaTypes.HAL_FORMS_JSON_VALUE)) //
				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_FORMS_JSON_VALUE))

				.andExpect(jsonPath("$.id", is(1))) //
				.andExpect(jsonPath("$.firstName", is("Frodo"))) //
				.andExpect(jsonPath("$.lastName", is("Baggins"))) //
				.andExpect(jsonPath("$.role", is("ring bearer")))

				.andExpect(jsonPath("$._templates.default.method", is("put")))
				.andExpect(jsonPath("$._templates.default.properties[0].name", is("firstName")))
				.andExpect(jsonPath("$._templates.default.properties[0].required", is(true)))
				.andExpect(jsonPath("$._templates.default.properties[1].name", is("id")))
				.andExpect(jsonPath("$._templates.default.properties[1].required", is(true)))
				.andExpect(jsonPath("$._templates.default.properties[2].name", is("lastName")))
				.andExpect(jsonPath("$._templates.default.properties[2].required", is(true)))
				.andExpect(jsonPath("$._templates.default.properties[3].name", is("role")))
				.andExpect(jsonPath("$._templates.default.properties[3].required", is(true)))

				.andExpect(jsonPath("$._links.self.href", is("http://localhost/companies/1")))
				.andExpect(jsonPath("$._links.companies.href", is("http://localhost/companies")));
	}
}