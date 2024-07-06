package com.malajmi.elm_wallet.wallet;

import com.malajmi.elm_wallet.user.WalletUserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet")
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "wallet_id", nullable = false)
    private UUID walletId;
    @Column(name = "balance", nullable = false, scale = 2, precision = 10)
    private BigDecimal balance;
    @Version
    private Long version;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private WalletUserEntity user;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_at")
    private Instant updatedAt;
}
