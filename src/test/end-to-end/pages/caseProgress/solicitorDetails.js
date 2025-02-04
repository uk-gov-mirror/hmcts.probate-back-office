'use strict';

const commonConfig = require('src/test/end-to-end/pages/common/commonConfig');

module.exports = async function (caseProgressConfig) {
    const I = this;
    await I.waitForElement('#solsSolicitorFirmName');
    await I.fillField('#solsSolicitorFirmName', caseProgressConfig.solFirmName);
    await I.click('#solsSolicitorIsExec-Yes');
    await I.fillField('#solsSOTForenames', caseProgressConfig.solFirstname);
    await I.fillField('#solsSOTSurname', caseProgressConfig.solSurname);
    await I.click(`#solsSolicitorIsMainApplicant-${caseProgressConfig.solIsMainApplicant ? 'Yes' : 'No'}`);
    if (!caseProgressConfig.solIsMainApplicant) {
        const locator = {css: `#solsSolicitorIsApplying-${caseProgressConfig.solIsApplying ? 'Yes' : 'No'}`};
        await I.waitForClickable(locator);
        await I.click(locator);
        if (!caseProgressConfig.solIsApplying) {
            await I.waitForVisible({css: '#solsSolicitorNotApplyingReason'});
            await I.selectOption({css: '#solsSolicitorNotApplyingReason'}, '1: MentallyIncapable');
        }
    }
    await I.click('#solsSolicitorAddress_solsSolicitorAddress a');
    await I.fillField('#solsSolicitorAddress_AddressLine1', caseProgressConfig.solAddr1);
    await I.fillField('#solsSolicitorAddress_PostTown', caseProgressConfig.solAddrTown);
    await I.fillField('#solsSolicitorAddress_County', caseProgressConfig.solAddrCounty);
    await I.fillField('#solsSolicitorAddress_PostCode', caseProgressConfig.solAddrPostcode);
    await I.fillField('#solsSolicitorAddress_Country', caseProgressConfig.solAddrCountry);
    await I.fillField('#solsSolicitorAppReference', caseProgressConfig.ref);
    await I.fillField('#solsSolicitorEmail', caseProgressConfig.solEmail);
    await I.fillField('#solsSolicitorPhoneNumber', caseProgressConfig.solPhone);
    await I.waitForNavigationToComplete(commonConfig.continueButton);
};
