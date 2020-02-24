package org.xyz.translateapp.translate;

import org.springframework.stereotype.Service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@Service
public class TranslateService {

    public String translateText(String sourceLang,String destinationLang,String text) {
        Translate service = TranslateOptions.getDefaultInstance().getService();
        Translation translation = service.translate(text,
                Translate.TranslateOption.sourceLanguage(sourceLang),
                Translate.TranslateOption.targetLanguage(destinationLang),
                Translate.TranslateOption.model("nmt"));

        return translation.getTranslatedText();
    }
}
