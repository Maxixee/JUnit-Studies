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

    @DisplayName("Given Existinf Email When Save Person then Throws Exception")
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
}