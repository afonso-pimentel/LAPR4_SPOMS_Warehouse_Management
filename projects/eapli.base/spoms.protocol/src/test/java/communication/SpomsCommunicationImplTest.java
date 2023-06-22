package communication;

import enums.MessageCode;
import enums.ProtocolVersion;
import models.SpomsPacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpomsCommunicationImplTest {
    @Mock
    private SpomsReceiver mockSpomsReceiver;

    @Mock
    private SpomsSender mockSpomsSender;

    @Mock
    private SpomsPacketListener mockPacketListener;

    private SpomsCommunicationImpl spomsCommunication;

    @BeforeEach
    public void createMocks() {
        MockitoAnnotations.openMocks(this);
        spomsCommunication = new SpomsCommunicationImpl(mockSpomsReceiver, mockSpomsSender);
    }

    @Test
    public void NoReponse_AfterTimeOut_ThrowsException() throws IOException {
        //Arrange
        var message = "test message";
        var request = new SpomsPacket(MessageCode.COMMTEST, ProtocolVersion.VERSION_1, message.length(), 10, "test message");
        var differentRequestedResponse = MessageCode.STATUS_REQUEST;
        doNothing().when(mockSpomsSender).send(any(SpomsPacket.class));
        //Act & Assert
        assertThrows(TimeoutException.class, () -> spomsCommunication.getResponse(request,differentRequestedResponse));
        verify(mockSpomsSender).send(any(SpomsPacket.class));
    }

}