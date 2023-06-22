package strategies.strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eapli.base.Application;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.ordersmanagement.application.ChangeOrderStatusService;
import eapli.base.ordersmanagement.application.algorithms.AlgorithmsStrategy;
import eapli.base.ordersmanagement.application.algorithms.AlgorithmsStrategyFactory;
import eapli.base.ordersmanagement.application.algorithms.AlgorithmsStrategyFactoryImpl;
import eapli.base.ordersmanagement.application.algorithms.AlgorithmsStrategyList;
import eapli.base.ordersmanagement.domain.OrderStatus;
import eapli.base.ordersmanagement.domain.Order_;
import eapli.base.warehousemanagement.application.AgvService;
import eapli.base.warehousemanagement.domain.Agv;
import eapli.base.warehousemanagement.domain.AgvStatus;
import eapli.base.warehousemanagement.repositories.AgvRepository;
import enums.MessageCode;
import enums.ProtocolVersion;
import models.OrderTaskAssignment;
import models.SpomsPacket;
import models.SpomsPacketBuilder;

import java.util.Optional;


public class NewTaskStrategy implements AgvManagerStrategy{

    @Override
    public SpomsPacket PerformStrategy(SpomsPacket packetToHandle) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String result;

        System.out.println("Received Request for a task...");

        try {
            Long agvId = Long.parseLong(packetToHandle.data());
            AgvRepository agvRepository = PersistenceContext.repositories().agvs();

            if(!agvRepository.containsOfIdentity(agvId))
                return new SpomsPacketBuilder()
                        .messageCode(MessageCode.NEW_TASK_RESPONSE)
                        .protocolVersion(ProtocolVersion.VERSION_1)
                        .data(
                                objectMapper.writeValueAsString(
                                        new OrderTaskAssignment("There is no agv with id " + agvId)
                                )
                        ).build();

            Agv agv = agvRepository.ofIdentity(agvId).get();

            if(agv.CurrentStatus() != AgvStatus.IDLE)
                return new SpomsPacketBuilder()
                        .messageCode(MessageCode.NEW_TASK_RESPONSE)
                        .protocolVersion(ProtocolVersion.VERSION_1)
                        .data(
                                objectMapper.writeValueAsString(
                                        new OrderTaskAssignment("That agv is currently working")
                                )
                        ).build();

            AlgorithmsStrategyList algType = Enum.valueOf(AlgorithmsStrategyList.class, Application.settings().getOrderAlgorithm());

            AlgorithmsStrategyFactory algFac = new AlgorithmsStrategyFactoryImpl();

            AlgorithmsStrategy<Optional<Order_>> alg = algFac.GetStrategy(algType);

            Optional<Order_> oldestOrder =  alg.execute(null);

            if (!oldestOrder.isPresent())
                return new SpomsPacketBuilder()
                        .messageCode(MessageCode.NEW_TASK_RESPONSE)
                        .protocolVersion(ProtocolVersion.VERSION_1)
                        .data(
                                objectMapper.writeValueAsString(
                                        new OrderTaskAssignment("There is no orders to be prepared.")
                                )
                        ).build();

            Order_ order = oldestOrder.get();

            ChangeOrderStatusService orderService = new ChangeOrderStatusService(PersistenceContext.repositories().orders());

            orderService.changeOrderStatus(order, OrderStatus.IN_PREPARATION);

            AgvService agvService = new AgvService(agvRepository);

            agvService.changeAgvStatus(agv, AgvStatus.WORKING);

            result = objectMapper.writeValueAsString(new OrderTaskAssignment(order.identity()));
        }catch (Exception ex)
        {
            result = objectMapper.writeValueAsString(new OrderTaskAssignment(ex.getMessage()));
        }

        return new SpomsPacketBuilder()
                .messageCode(MessageCode.NEW_TASK_RESPONSE)
                .protocolVersion(ProtocolVersion.VERSION_1)
                .data(result)
                .build();
    }
}
