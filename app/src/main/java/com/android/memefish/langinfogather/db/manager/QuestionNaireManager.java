package com.android.memefish.langinfogather.db.manager;

import com.android.memefish.langinfogather.db.QuestionNaire;
import com.android.memefish.langinfogather.util.UserUtil;

import java.util.List;

public class QuestionNaireManager extends BaseManager {

    private QuestionNaireManager(){}

    public static boolean insert(QuestionNaire questionNaire){
        long id = questionNaireDao.insertOrReplace(questionNaire);
        return id > 0;
    }

    public static List<QuestionNaire> listQuestionNaire(Long obligeeId){
        return questionNaireDao.queryRaw("WHERE USER_ID=? AND REGION=? AND OBLIGEE_ID=?", UserUtil.getInstance().getUserId(),""+UserUtil.getInstance().getRegion(),""+obligeeId);
    }

    public static void delete(Long obligeeId){
        List<QuestionNaire> questionNaires = listQuestionNaire(obligeeId);
        for (QuestionNaire questionNaire : questionNaires){
            if(questionNaire.getObligee().equals(""+obligeeId)){
                questionNaireDao.delete(questionNaire);
            }
        }
    }
}
