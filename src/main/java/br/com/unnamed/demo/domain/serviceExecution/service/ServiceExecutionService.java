// package br.com.unnamed.demo.domain.serviceExecution.service;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.util.List;
// import java.util.NoSuchElementException;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;

// import br.com.unnamed.demo.domain.payment.model.Payment;
// import br.com.unnamed.demo.domain.payment.model.enums.PaymentStatus;
// import br.com.unnamed.demo.domain.payment.model.valueObjects.PaymentMethod;
// import
// br.com.unnamed.demo.domain.serviceExecution.builder.ServiceExecutionBuilder;
// import br.com.unnamed.demo.domain.serviceExecution.model.ServiceExecution;
// import
// br.com.unnamed.demo.domain.serviceExecution.model.enums.ServicePaymentStatus;
// import br.com.unnamed.demo.domain.serviceExecution.model.enums.ServiceStatus;
// import
// br.com.unnamed.demo.domain.serviceExecution.repository.ServiceExecutionRepository;
// import
// br.com.unnamed.demo.domain.serviceExecution.util.InstallmentsCalculator;
// import br.com.unnamed.demo.domain.tutor.model.Pet;
// import br.com.unnamed.demo.domain.tutor.model.Tutor;
// import br.com.unnamed.demo.domain.tutor.service.TutorService;

// @Service
// public class ServiceExecutionService {

// private final ServiceExecutionRepository repo;
// private final TutorService tutorService;

// public ServiceExecutionService(ServiceExecutionRepository repo, TutorService
// tutorService) {
// this.repo = repo;
// this.tutorService = tutorService;
// }

// public ServiceExecution findById(Long id) {

// return repo.findById(id).orElseThrow(() -> new
// NoSuchElementException("Service execution not found"));

// }

// public ServiceExecution save(ServiceExecution serviceExecution) {

// return repo.save(serviceExecution);

// }

// public Page<ServiceExecution> searchWithOptionalFilters(String name,
// LocalDate date, ServiceStatus status,
// ServicePaymentStatus paymentStatus, Pageable pageable) {

// return repo.findWithOptionalFilters(name, date, status, paymentStatus,
// pageable);

// }

// public List<ServiceExecution> findByStatusAndDate(ServiceStatus status,
// LocalDate date) {

// return repo.findByStatusAndDate(status, date);

// }

// public void updateTutorAndPet(Long serviceExecutionId, Long tutorId, Long
// petId) {

// ServiceExecution serviceExecution = findById(serviceExecutionId);
// serviceExecution.updateTutorAndPet(tutor, pet);
// repo.save(serviceExecution);

// }

// public void start(ServiceExecution s) {

// s.start();
// repo.save(s);

// }

// public void finish(ServiceExecution s) {

// s.markAsDone();
// repo.save(s);

// }

// public void cancel(ServiceExecution s) {

// s.cancel();
// repo.save(s);

// }

// public boolean existsNotPaid() {

// return !repo.existsWithPendingPayment().isEmpty();

// }

// public List<ServiceExecution> findTop10ByPetIdOrderByDateDesc(Long id) {

// return repo.findTop10ByPetIdOrderByDateDesc(id);

// }

// public void addPayment(ServiceExecution s, PaymentMethod method, Integer
// installments, BigDecimal amount) {

// if (installments == null)
// installments = 1;

// if (amount.compareTo(s.calculateTotal()) > 0) {

// amount = s.calculateTotal();

// }

// List<BigDecimal> amountsDistributed =
// InstallmentsCalculator.calculateInstallmentsDistributed(amount,
// installments);

// for (Integer i = 0; i < amountsDistributed.size(); i++) {

// Payment p = new Payment(
// LocalDate.now(),
// method,
// amountsDistributed.get(i),
// PaymentStatus.TEMPORARY,
// s.getPet().getName(),
// s.getTutor().getName());

// s.addPayment(p);

// }

// repo.save(s);

// }

// public void removePayment(ServiceExecution s, Payment p) {

// if (s.getServiceStatus() != ServiceStatus.COMPLETED) {

// s.removePayment(p);
// p.removeLinkToServiceExecution();

// }

// if (s.getServiceStatus() == ServiceStatus.COMPLETED) {

// p.updateStatus(PaymentStatus.CANCELLED);

// }

// repo.save(s);

// }

// public void finish(Long serviceId) {

// ServiceExecution service = findById(serviceId);
// service.markAsPaid();
// repo.save(service);

// }

// public ServiceExecution create(Tutor tutor, Pet pet) {

// return repo.save(
// new ServiceExecutionBuilder()
// .tutor(tutor)
// .pet(pet)
// .date(LocalDate.now())
// .status(ServiceStatus.PENDING)
// .paymentStatus(ServicePaymentStatus.NOT_PAID)
// .build());

// }
// }
