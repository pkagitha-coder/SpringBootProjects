package org.xyz.translateapp.translate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslateResource {

    @Autowired
    private TranslateService service;

    @GetMapping("/translate/{srcLang}/to/{targetLang}")
    public @ResponseBody TranslationResp translate(@PathVariable String srcLang, @PathVariable String targetLang, @RequestBody String text) {
        String translatedText = service.translateText(srcLang, targetLang, text);
        TranslationResp resp = new TranslationResp();
        resp.setSourceLanguage(srcLang);
        resp.setTargetLanguage(targetLang);
        resp.setText(text);
        resp.setTranslatedText(translatedText);

        return resp;
    }

}
