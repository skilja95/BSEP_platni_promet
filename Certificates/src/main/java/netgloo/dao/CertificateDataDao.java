package netgloo.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import netgloo.models.CertificateData;

@Transactional
public interface CertificateDataDao extends CrudRepository<CertificateData, Integer> {

	public CertificateData findByCertificateDataSN(String certificateDataSN);

	public CertificateData findByCertificateDataCA(String certificateDataCA);

	public CertificateData findByCertificateDataAlias(String certificateDataAlias);

}
