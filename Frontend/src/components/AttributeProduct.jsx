const AttributeProduct = ({ features }) => {
  localStorage.getItem("userId");
  return (
    <>
      <div className="features">
        <h4 className="text-uppercase ">Features </h4>
        <div className="features-list">
          {features &&
            features.map((feature) => (
              <div key={feature.id} className="feature-item">
                <div
                  className="feature-icon"
                  dangerouslySetInnerHTML={{ __html: feature.icon }}
                />
                <div className="feature-name ">{feature.name}</div>
              </div>
            ))}
        </div>
      </div>
    </>
  );
};

export default AttributeProduct;
