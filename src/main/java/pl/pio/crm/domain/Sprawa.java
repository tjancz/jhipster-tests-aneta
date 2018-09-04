package pl.pio.crm.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Sprawa.
 */
@Entity
@Table(name = "sprawa")
public class Sprawa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "stan")
    private String stan;

    @Column(name = "kwota")
    private String kwota;

    @Column(name = "klient")
    private String klient;

    @Column(name = "adres")
    private String adres;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "status")
    private String status;

    @Column(name = "numer")
    private String numer;

    @Column(name = "utworzona")
    private LocalDate utworzona;

    @Column(name = "data")
    private LocalDate data;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStan() {
        return stan;
    }

    public Sprawa stan(String stan) {
        this.stan = stan;
        return this;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getKwota() {
        return kwota;
    }

    public Sprawa kwota(String kwota) {
        this.kwota = kwota;
        return this;
    }

    public void setKwota(String kwota) {
        this.kwota = kwota;
    }

    public String getKlient() {
        return klient;
    }

    public Sprawa klient(String klient) {
        this.klient = klient;
        return this;
    }

    public void setKlient(String klient) {
        this.klient = klient;
    }

    public String getAdres() {
        return adres;
    }

    public Sprawa adres(String adres) {
        this.adres = adres;
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTelefon() {
        return telefon;
    }

    public Sprawa telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getStatus() {
        return status;
    }

    public Sprawa status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumer() {
        return numer;
    }

    public Sprawa numer(String numer) {
        this.numer = numer;
        return this;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public LocalDate getUtworzona() {
        return utworzona;
    }

    public Sprawa utworzona(LocalDate utworzona) {
        this.utworzona = utworzona;
        return this;
    }

    public void setUtworzona(LocalDate utworzona) {
        this.utworzona = utworzona;
    }

    public LocalDate getData() {
        return data;
    }

    public Sprawa data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sprawa sprawa = (Sprawa) o;
        if (sprawa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sprawa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sprawa{" +
            "id=" + getId() +
            ", stan='" + getStan() + "'" +
            ", kwota='" + getKwota() + "'" +
            ", klient='" + getKlient() + "'" +
            ", adres='" + getAdres() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", status='" + getStatus() + "'" +
            ", numer='" + getNumer() + "'" +
            ", utworzona='" + getUtworzona() + "'" +
            ", data='" + getData() + "'" +
            "}";
    }
}
