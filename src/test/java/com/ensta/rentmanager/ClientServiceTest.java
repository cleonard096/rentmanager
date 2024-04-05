package com.ensta.rentmanager;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ServiceException;
public class ClientServiceTest {
    private ClientDao clientDaoMock;
    private ClientService clientService;

    @Before
    public void setUp() {
        clientDaoMock = mock(ClientDao.class);
        clientService = new ClientService(clientDaoMock);
    }

    @Test
    public void testFindByIdClient() throws ServiceException, DaoException {
        long clientId = 1L;
        Client expectedClient = new Client();
        expectedClient.setClientId((int) clientId);
        expectedClient.setNom("Doe");
        expectedClient.setPrenom("John");
        expectedClient.setEmail("john.doe@example.com");
        expectedClient.setDateNaissance(LocalDate.of(1990, 1, 1));
        when(clientDaoMock.findById(clientId)).thenReturn(expectedClient);
        Client foundClient = clientService.findById(clientId);
        verify(clientDaoMock).findById(clientId);
        assertNotNull(foundClient);
        assertEquals(expectedClient, foundClient);
    }

    @Test
    public void testDeleteClient() throws ServiceException, DaoException {
        Client client = new Client();
        client.setClientId(1);
        client.setNom("Doe");
        client.setPrenom("John");
        client.setEmail("john.doe@example.com");
        client.setDateNaissance(LocalDate.of(1990, 1, 1));
        when(clientDaoMock.delete(client)).thenReturn((long) client.getClientId());
        long deletedClientId = clientService.delete(client);
        verify(clientDaoMock).delete(client);
        assertEquals(client.getClientId(), deletedClientId);
    }

    @Test(expected = ServiceException.class)
    public void testCreateClientWithEmptyNom() throws ServiceException {
        Client client = new Client();
        client.setNom("");
        client.setPrenom("John");
        client.setEmail("john@example.com");
        client.setDateNaissance(LocalDate.of(2000, 1, 1));
        clientService.create(client);
    }

    @Test(expected = ServiceException.class)
    public void testCreateClientWithInvalidNomLength() throws ServiceException {
        Client client = new Client();
        client.setNom("Jo");
        client.setPrenom("John");
        client.setEmail("john@example.com");
        client.setDateNaissance(LocalDate.of(2000, 1, 1));
        clientService.create(client);
    }


    @Test(expected = ServiceException.class)
    public void testModifyClientWithEmptyNom() throws ServiceException {
        long clientId = 1L;
        String newNom = "";
        String newPrenom = "John";
        String newEmail = "john@example.com";
        LocalDate newDateNaissance = LocalDate.of(2000, 1, 1);
        clientService.modify(clientId, newNom, newPrenom, newEmail, newDateNaissance);
    }

    @Test(expected = ServiceException.class)
    public void testModifyClientWithInvalidNomLength() throws ServiceException {
        long clientId = 1L;
        String newNom = "Jo";
        String newPrenom = "John";
        String newEmail = "john@example.com";
        LocalDate newDateNaissance = LocalDate.of(2000, 1, 1);
        clientService.modify(clientId, newNom, newPrenom, newEmail, newDateNaissance);
    }
}