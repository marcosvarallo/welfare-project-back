package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.enums.AppUserRole;
import com.tcc.welfare.Welfare.model.HealthProfessional;
import com.tcc.welfare.Welfare.repository.EmailSender;
import com.tcc.welfare.Welfare.services.token.ConfirmationToken;
import com.tcc.welfare.Welfare.services.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class HealthProRegistrationService {

    private final HealthProfessionalService healthProfessionalService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest healthProfessionalRequest){

        boolean isValidEmail = emailValidator.
                validate(healthProfessionalRequest.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        String token = healthProfessionalService.signUpHealthPro(
                new HealthProfessional(
                        healthProfessionalRequest.getFirstName(),
                        healthProfessionalRequest.getLastName(),
                        healthProfessionalRequest.getEmail(),
                        healthProfessionalRequest.getPassword(),
                        healthProfessionalRequest.getAge(),
                        healthProfessionalRequest.getType(),
                        AppUserRole.ADMIN
                )
        );
        String link = "https://welfare-backend.herokuapp.com/api/v1/healthPro/confirm?token=" + token;
        emailSender.send(
                healthProfessionalRequest.getEmail(),
                buildEmail(healthProfessionalRequest.getFirstName(), link));
        return token;
    }

    @Transactional
    public String confirmToken(String token) {

        String status = "";

        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (confirmationToken.getConfirmedAt() != null) {
            status = "Email já confirmado";
            return status;
        } else if (expiredAt.isBefore(LocalDateTime.now())) {
            status = "Token Expirado";
            String healthProfessionalEmail = confirmationToken.getHealthProfessional().getEmail();
            Long healthProfessionalId = healthProfessionalService.getHealthProfessionalIdByEmail(healthProfessionalEmail);
            healthProfessionalService.deleteHealthProfessional(healthProfessionalId);
            return status;
        } else {
            status = "Ativado";
            confirmationTokenService.setConfirmedAt(token);
            healthProfessionalService.enableHealthProfessional(
                    confirmationToken.getHealthProfessional().getEmail());
            return status;
        }
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Welfare - Confirmação de Email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Olá " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Obrigado por se registrar no Welfare. Por favor, clique no link abaixo para ativar sua conta: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Ativar agora</a> </p></blockquote>\n O link vai expirar em 15 minutos. <p>Vejo você logo ;)</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
