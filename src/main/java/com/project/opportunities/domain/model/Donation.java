package com.project.opportunities.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "donations")
@Inheritance(strategy = InheritanceType.JOINED)
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private LocalDateTime donationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationType donationType;

    private String donorName;

    private String donorEmail;

    public enum Currency {
        USD("USD", "$", "US Dollar"),
        EUR("EUR", "€", "Euro"),
        GBP("GBP", "£", "British Pound"),
        JPY("JPY", "¥", "Japanese Yen"),
        CHF("CHF", "CHF", "Swiss Franc"),
        CNY("CNY", "¥", "Chinese Yuan"),
        UAH("UAH", "₴", "Ukrainian Hryvnia"),
        PLN("PLN", "zł", "Polish Złoty"),
        CAD("CAD", "$", "Canadian Dollar"),
        AUD("AUD", "$", "Australian Dollar"),
        NZD("NZD", "$", "New Zealand Dollar"),
        SGD("SGD", "$", "Singapore Dollar"),
        HKD("HKD", "$", "Hong Kong Dollar"),
        SEK("SEK", "kr", "Swedish Krona"),
        NOK("NOK", "kr", "Norwegian Krone"),
        DKK("DKK", "kr", "Danish Krone"),
        INR("INR", "₹", "Indian Rupee"),
        AED("AED", "د.إ", "UAE Dirham"),
        SAR("SAR", "﷼", "Saudi Riyal"),
        ILS("ILS", "₪", "Israeli Shekel");

        private final String code;
        private final String symbol;
        private final String displayName;

        Currency(String code, String symbol, String displayName) {
            this.code = code;
            this.symbol = symbol;
            this.displayName = displayName;
        }

        public String getCode() {
            return code;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return code;
        }

        public static Currency fromCode(String code) {
            return Arrays.stream(Currency.values())
                    .filter(currency -> currency.getCode().equals(code))
                    .findFirst()
                    .orElseThrow(()
                            -> new IllegalArgumentException("Unknown currency code: " + code));
        }
    }

    public enum DonationType {
        GENERAL_DONATION,
        PROJECT_DONATION
    }

}
