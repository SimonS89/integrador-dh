import React, { memo } from "react";

const Policies = ({ policies }) => {
  return (
    <div className="row">
      {policies && (
        <>
          <h4 className="text-uppercase ">Product Policies </h4>
          {Object.entries(policies).map(([policyKey, policyValue], index) => (
              // Omitir la propiedad 'id'
              policyKey !== "id" && (
            <div key={index} className="col-md-4">
              <div className="policy-item">
                <h5 className="text-uppercase ">{policyKey}</h5>
                <p>{policyValue}</p>
              </div>
          
            </div>
              )
          ))}
        </>
      )}
    </div>
  );
};

export default memo(Policies);
