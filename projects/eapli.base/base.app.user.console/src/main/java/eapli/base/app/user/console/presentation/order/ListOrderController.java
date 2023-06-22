package eapli.base.app.user.console.presentation.order;

import eapli.base.customerusermanagement.domain.VatCode;
import models.OrderDTO;

public interface ListOrderController {
    Iterable<OrderDTO> orderStatusFromClient(VatCode vatCode);
}
