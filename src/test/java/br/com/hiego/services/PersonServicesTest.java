package br.com.hiego.services;

import static org.mockito.BDDMockito.*;

import br.com.hiego.exceptions.ResourceNotFoundException;
import br.com.hiego.model.Person;
import br.com.hiego.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

    @Mock
    private PersonRepository repository;
    @InjectMocks
    private PersonServices services;

    private Person person;

    @BeforeEach
    public void setup(){
        person = new Person("Hiego",
                "Barreto",
                "hiego@email.com",
                "Test street",
                "Male");
    }

    @DisplayName("Given Person Object When Save Person then Return Person Object")
    @Test
    void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject(){
        //Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person)).willReturn(person);

        //When
        Person savedPerson = services.create(person);

        //Assert
        assertNotNull(savedPerson);
        assertEquals("Hiego", savedPerson.getFirstName());
    }

    @DisplayName("Given Existing Email When Save Person then Throws Exception")
    @Test
    void testGivenExistingEmail_WhenSavePerson_thenThrowsException(){
        //Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person));

        //When
        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(person);
        });

        //Assert
        verify(repository, never()).save(any(Person.class));
    }

    @DisplayName("Given Persons List When Find All Persons then Return Persons List")
    @Test
    void testGivenPersonsList_WhenFindAllPersons_thenReturnPersonsList(){
        //Arrange
        Person person1 = new Person("Anakin",
                "Skywalker",
                "anakin@email.com",
                "Death Star",
                "Male");
        given(repository.findAll()).willReturn(List.of(person, person1));

        //When
        List<Person> personList = services.findAll();

        //Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }

    @DisplayName("Given Empty Persons List When Find All Persons then Return Empty Persons List")
    @Test
    void testGivenEmptyPersonsList_WhenFindAllPersons_thenReturnEmptyPersonsList(){
        //Arrange
        given(repository.findAll()).willReturn(Collections.emptyList());

        //When
        List<Person> personList = services.findAll();

        //Assert
        assertTrue(personList.isEmpty());
        assertEquals(0, personList.size());
    }

    @DisplayName("Given Person Id When Find By Id then Return Person Object")
    @Test
    void testGivenPersonId_WhenFindById_thenReturnPersonObject(){
        //Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(person));

        //When
        Person savedPerson = services.findById(1L);

        //Assert
        assertNotNull(savedPerson);
        assertEquals("Hiego", savedPerson.getFirstName());
    }

    @DisplayName("Given Person Object When Update Person then Return Updated Person Object")
    @Test
    void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatedPersonObject(){
        //Arrange
        person.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person));

        person.setFirstName("Gwyn");
        person.setEmail("hiego@email.com");

        given(repository.save(person)).willReturn(person);

        //When
        Person updatedPerson = services.update(person);

        //Assert
        assertNotNull(updatedPerson);
        assertEquals("Gwyn", updatedPerson.getFirstName());
        assertEquals("hiego@email.com", updatedPerson.getEmail());
    }

    @DisplayName("Given Person Id When Delete Person then Do Nothing")
    @Test
    void testGivenPersonId_WhenDeletePerson_thenDoNothing(){
        //Arrange
        person.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person));
        willDoNothing().given(repository).delete(person);

        //When
        services.delete(person.getId());

        //Assert
        verify(repository, times(1)).delete(person);
    }
}