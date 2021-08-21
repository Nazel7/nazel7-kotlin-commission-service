//package com.digitalcolliers.commissionservice;
//
//
//import com.digitalcolliers.commissionservice.controllers.CommissionController;
//import com.digitalcolliers.commissionservice.entities.FeeTransactionPercentage;
//import com.digitalcolliers.commissionservice.entities.TransactionModel;
//import com.digitalcolliers.commissionservice.exceptions.advices.GlobalExceptionAvice;
//import com.digitalcolliers.commissionservice.repositories.FeeTransactionRepo;
//import com.digitalcolliers.commissionservice.repositories.TransactionRepo;
//import com.digitalcolliers.commissionservice.services.TransactionService;
//import com.digitalcolliers.commissionservice.usecases.dtos.responses.CustomerDto;
//import lombok.Data;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//@ContextConfiguration(classes = {CommissionController.class})
//@ExtendWith(SpringExtension.class)
//class CommissionServiceTest {
//
//    @MockBean
//    private TransactionRepo transactionRepo;
//
//    @MockBean
//    private FeeTransactionRepo feeRepo;
//
//    @MockBean
//    private ApplicationEventPublisher applicationEvent;
//
//    @MockBean
//    HashSet<CustomerDto> hashSet;
//
//    @MockBean
//    private Page<TransactionModel> transactionModelsPage;
//
//    @MockBean
//    private TransactionService transactionService;
//
//    @Autowired
//    private CommissionController commissionController;
//
//    private MockMvc mMvc;
//
////    @BeforeEach
//    public void setUp() {
//        // Initiate mocked variables for test cases.
//        MockitoAnnotations.initMocks(this);
//
//       transactionService = new TransactionService(transactionRepo, feeRepo, applicationEvent, hashSet);
//
//        commissionController = new CommissionController(transactionService);
//
//        mMvc = MockMvcBuilders.standaloneSetup(commissionController)
//                              .setControllerAdvice(new GlobalExceptionAvice())
//                              .build();
//    }
//
//    @Test
//    public void validate_request_for_commission_returns_200_for_valid_resquestBody()
//            throws Exception {
//
//        // Given
//        String URL = "/commission-request?elasticCustomerId=1";
//
//        String commissionString = new TestUtil().objectStringConverter("1,2,3,All");
//        TransactionModel model=
//                new TransactionModel(
//                "01", 227L, new BigDecimal(300),
//                "Gafar", 2L, "Olanipekun", new Date());
//
//        FeeTransactionPercentage feeTransactionPercentage=
//                new FeeTransactionPercentage("23", new BigDecimal(200), 4.5);
//
//        CustomerDto customerDto=
//                new CustomerDto(1L, "Gafar", "Olanipekun",
//                11, new BigDecimal(1200), new BigDecimal(200), new Date());
//
//        List<TransactionModel> transactionModelList= new ArrayList<>();
//        transactionModelList.add(model);
//
//        List<FeeTransactionPercentage> feeTransactionPercentages= new ArrayList<>();
//        feeTransactionPercentages.add(feeTransactionPercentage);
//
//        // When
//        Mockito.when(transactionRepo.findTransactionModelByCustomerIdOrderByLastTransactionDateDesc(Mockito.anyLong()))
//               .thenReturn(transactionModelList);
//
//        Mockito.when(transactionRepo.findAll(Pageable.ofSize(100)))
//               .thenReturn(transactionModelsPage);
//
//        Mockito.when(feeRepo.findAll()).thenReturn(feeTransactionPercentages);
//        Mockito.when(transactionRepo.count()).thenReturn(2L);
//        Mockito.when(transactionRepo.saveAll(transactionModelList)).thenReturn(transactionModelList);
//        Mockito.when(feeRepo.count()).thenReturn(2L);
//        Mockito.when(feeRepo.saveAll(feeTransactionPercentages)).thenReturn(feeTransactionPercentages);
//        Mockito.when(hashSet.add(customerDto)).thenReturn(true);
////
////        // Then
////        mMvc.perform(MockMvcRequestBuilders.get(URL).contentType(
////                MediaType.APPLICATION_JSON))
////            .andExpect(MockMvcResultMatchers.status().isOk());
//
//
//    }
//
//}
