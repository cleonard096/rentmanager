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
    @Test
    public void testCountClients() throws ServiceException, DaoException {
        int expectedCount = 5;//à changer selon le nbr de client
        when(clientDaoMock.count()).thenReturn(expectedCount);
        int count = clientService.count();
        verify(clientDaoMock).count();
        assertEquals(expectedCount, count);
    }
}