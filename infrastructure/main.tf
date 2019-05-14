provider "azurerm" {
  version = "1.22.1"
}

locals {
  aseName = "core-compute-${var.env}"

  //probate_frontend_hostname = "probate-frontend-aat.service.core-compute-aat.internal"
  previewVaultName = "${var.raw_product}-aat"
  previewEnv= "aat"
  nonPreviewEnv = "${var.env}"
  nonPreviewVaultName = "${var.raw_product}-${var.env}"
  vaultName = "${(var.env == "preview" || var.env == "spreview") ? local.previewVaultName : local.nonPreviewVaultName}"
  localenv = "${(var.env == "preview" || var.env == "spreview") ? local.previewEnv : local.nonPreviewEnv}"
}

data "azurerm_key_vault" "probate_key_vault" {
  name = "${local.vaultName}"
  resource_group_name = "${local.vaultName}"
}

data "azurerm_key_vault_secret" "govNotifyApiKey" {
  name = "probate-bo-govNotifyApiKey"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "pdf_service_grantSignatureKey" {
  name = "probate-bo-grantSignatureKey"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "pdf_service_grantSignatureFile" {
  name = "probate-bo-grantSignatureFile"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "s2s_key" {
  name      = "microservicekey-probate-backend"
  vault_uri = "https://s2s-${local.localenv}.vault.azure.net/"
}

data "azurerm_key_vault_secret" "POSTGRES-USER" {
  name = "probatemandb-POSTGRES-USER"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "POSTGRES-PASS" {
  name = "probatemandb-POSTGRES-PASS"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "POSTGRES_HOST" {
  name = "probatemandb-POSTGRES-HOST"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "POSTGRES_PORT" {
  name = "probatemandb-POSTGRES-PORT"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "POSTGRES_DATABASE" {
  name = "probatemandb-POSTGRES-DATABASE"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "excelaEmail" {
  name = "excelaEmail"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "authTokenEmail" {
  name = "authTokenEmail"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "authTokenPassword" {
  name = "authTokenPassword"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "idamRedirectUrl" {
  name = "idamRedirectUrl"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "ftpSignature" {
  name = "ftpSignature"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "ftpEnv" {
  name = "ftpEnv"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

data "azurerm_key_vault_secret" "idamSecretProbate" {
  name = "idam-secret-probate"
  vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

module "probate-back-office" {
  source = "git@github.com:hmcts/moj-module-webapp.git?ref=master"
  product = "${var.product}-${var.microservice}"
  location = "${var.location}"
  env = "${var.env}"
  ilbIp = "${var.ilbIp}"
  is_frontend  = false
  subscription = "${var.subscription}"
  asp_name     = "${var.asp_name}"
  capacity     = "${var.capacity}"
  common_tags  = "${var.common_tags}"
  asp_rg       = "${var.asp_rg}"
  appinsights_instrumentation_key = "${var.appinsights_instrumentation_key}"

  app_settings = {

	  // Logging vars
    REFORM_TEAM = "${var.product}"
    REFORM_SERVICE_NAME = "${var.microservice}"
    REFORM_ENVIRONMENT = "${var.env}"

    DEPLOYMENT_ENV= "${var.deployment_env}"

    AUTH_PROVIDER_SERVICE_CLIENT_KEY = "${data.azurerm_key_vault_secret.s2s_key.value}"
    PDF_SERVICE_GRANTSIGNATURESECRETKEY = "${data.azurerm_key_vault_secret.pdf_service_grantSignatureKey.value}"
    PDF_SERVICE_GRANTSIGNATUREENCRYPTEDFILE = "${data.azurerm_key_vault_secret.pdf_service_grantSignatureFile.value}"

    PROBATE_POSTGRESQL_USER = "${data.azurerm_key_vault_secret.POSTGRES-USER.value}"
    PROBATE_POSTGRESQL_PASSWORD = "${data.azurerm_key_vault_secret.POSTGRES-PASS.value}"
    PROBATE_POSTGRESQL_DATABASE = "${data.azurerm_key_vault_secret.POSTGRES_DATABASE.value}"
    PROBATE_POSTGRESQL_HOSTNAME =  "${data.azurerm_key_vault_secret.POSTGRES_HOST.value}"
    PROBATE_POSTGRESQL_PORT = "${data.azurerm_key_vault_secret.POSTGRES_PORT.value}"

    AUTH_PROVIDER_SERVICE_CLIENT_BASEURL = "${var.auth_service_url}"
    PDF_SERVICE_URL = "${var.pdf_service_api_url}"
    PRINTSERVICE_HOST = "${var.printservice_host}"
    PRINTSERVICE_INTERNAL_HOST = "${var.printservice_internal_host}"
    CCD_GATEWAY_HOST = "${var.ccd_gateway_host}"
    SERVICES_CORECASEDATA_BASEURL = "${var.ccd_baseUrl}"
    IDAM_SERVICE_HOST = "${var.idam_service_host}"
    FEE_API_URL = "${var.fee_api_url}"
    EVIDENCE_MANAGEMENT_HOST = "${var.evidence_management_host}"
    NOTIFICATIONS_GOVNOTIFYAPIKEY = "${data.azurerm_key_vault_secret.govNotifyApiKey.value}"
    CCD_DATA_STORE_API_HOST = "${var.ccd_data_store_api}"
    java_app_name = "${var.microservice}"
    SEND_LETTER_SERIVCE_BASEURL = "${var.send_letter_base_url}"
    LOG_LEVEL = "${var.log_level}"
    EXCELA_EMAIL = "${data.azurerm_key_vault_secret.excelaEmail.value}"
    AUTH_TOKEN_EMAIL = "${data.azurerm_key_vault_secret.authTokenEmail.value}"
    AUTH_TOKEN_PASSWORD = "${data.azurerm_key_vault_secret.authTokenPassword.value}"
    IDAM_REDIRECT_URL = "${data.azurerm_key_vault_secret.idamRedirectUrl.value}"
    FTP_SIGNATURE = "${data.azurerm_key_vault_secret.ftpSignature.value}"
    FTP_ENV = "${data.azurerm_key_vault_secret.ftpEnv.value}"
    IDAM_SECRET = "${data.azurerm_key_vault_secret.idamSecretProbate.value}"
  }
}

