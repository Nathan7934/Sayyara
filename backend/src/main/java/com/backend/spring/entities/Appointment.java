package com.backend.spring.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;
import static lombok.AccessLevel.NONE;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @SequenceGenerator(name = "appointment_sequence", sequenceName = "appointment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "appointment_sequence")
    @Column(name = "appointment_id")
    private Long id;

    @JsonProperty(access = WRITE_ONLY)
    @ToString.Exclude
    @ManyToOne(optional = false)
    @JoinColumn(name = "shop_id", referencedColumnName = "shop_id")
    private Shop shop;

    @Transient
    @Getter(value = NONE)
    private long shopId = -1L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private VehicleOwner vehicleOwner;

    @Column(name = "start_date", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime startTime;

    @Column(name = "end_date", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime endTime;

    @ToString.Exclude
    @JsonProperty(access = WRITE_ONLY)
    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "service_id", referencedColumnName = "service_id", nullable = false)
    private Service service;

    @Transient
    @Getter(value = NONE)
    private String serviceName;

    private Boolean wasQuote;

    public Appointment(Shop shop, VehicleOwner vehicleOwner, LocalDateTime startTime, LocalDateTime endTime, Service service) {
        this.shop = shop;
        this.vehicleOwner = vehicleOwner;
        this.startTime = startTime;
        this.endTime = endTime;
        this.service = service;
    }

    public Long getShopId() {
        if (shop != null)
            return shop.getId();
        return shopId;
    }

    public String getServiceName() {
        if (service != null)
            return service.getName();
        return serviceName;
    }
}
