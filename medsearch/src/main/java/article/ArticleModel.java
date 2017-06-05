package article;

import textProcessing.*;
import lombok.Data;
import textProcessing.Dictionary;

import java.util.*;

/**
 * Created by wilek on 2017-05-15.
 */
@Data
public class ArticleModel {

    private String date = "";
    private String title = "";
    private String author = "";
    private String content = "";
    private String id = "";
    private List<String> keyWords;

    private List<Term> indexedTitle;
    private List<Term> indexedContent;
    private List<Term> indexedKeyWords;
    private List<Term> indexedAll;

    public ArticleModel(){
        keyWords = new ArrayList<>();

        indexedTitle = null;
        indexedKeyWords = null;
        indexedKeyWords = null;
        indexedAll = null;
    }
    
    public void addKeyWord(String keyWord){ this.keyWords.add(keyWord);}

    public void indexArticle(Dictionary dictionary){
        if(indexedAll==null) {
            indexedTitle = Indexer.indexText(title, dictionary);
            indexedContent = Indexer.indexText(content, dictionary);

            indexedKeyWords = new ArrayList<>();
            for (String keyWord : keyWords) {
                indexedKeyWords.addAll(Indexer.indexText(keyWord, dictionary));
            }

            indexedAll = new ArrayList<>();
            indexedAll.addAll(indexedTitle);
            indexedAll.addAll(indexedContent);
            indexedAll.addAll(indexedKeyWords);
        }
    }


    public String toString(){
        String res = "Title: "+this.title+"\n"+this.content+"\nKey words: ";
        for(String k: this.keyWords) res = res + k + ", ";
        return res;
    }


    public static List<ArticleModel> testArticles(){
        List<ArticleModel> result = new ArrayList<>();

        ArticleModel a;
        a = new ArticleModel();
        a.setTitle( "Vitamin D Deficiency in Patients With Chronic Tension-Type Headache: A Case-Control Study.");
        a.addKeyWord("chronic-tension-type headache");
        a.addKeyWord("generalized pain");
        a.addKeyWord("migraine");
        a.addKeyWord("osteomalacia");
        a.addKeyWord("tension-type headache");
        a.addKeyWord("vitamin D");
        a.setContent(a.getContent() + "To see the interrelation between chronic tension-type headache (CTTH) and serum vitamin D levels." + "\n");
        a.setContent(a.getContent() + "Several studies have suggested an association between chronic pain and vitamin D deficiency. Anecdotal evidence suggests that vitamin D deficiency may be associated with tension-type headache and migraine." + "\n");
        a.setContent(a.getContent() + "This case-control study was carried out to examine the association between CTTH and serum 25-hydroxy vitamin (25(OH) D) levels. One hundred consecutive adult (>18 years) patients with CTTH and 100 matched healthy controls were enrolled." + "\n");
        a.setContent(a.getContent() + "The serum 25(OH) D levels were significantly lower in CTTH patients than in the controls (14.7 vs 27.4 ng/mL). The prevalence of vitamin D deficiency (serum 25 (OH) D < 20 ng/mL) was greater in patients with CTTH (71% vs 25%). CTTH patients had a significantly high prevalence of musculoskeletal pain (79% vs 57%), muscle weakness (29%vs 10%), muscle tenderness score (7.5 vs 1.9), and bone tenderness score (3.0 vs 0.8) in comparison to controls. CTTH patients with vitamin D deficient group (<20 ng/mL) had a higher prevalence of musculoskeletal pain (58% vs 31%), muscle weakness (38%vs 7%), muscle and bone tenderness score, associated fatigue (44% vs 17%) and more prolonged course (15.5 months vs 11.2 months). A strong positive correlation was noted between serum vitamin D levels and total muscle tenderness score (R(2)  = 0. 7365) and total bone tenderness score (R(2)  = 0. 6293)." + "\n");
        a.setContent(a.getContent() + "Decreased serum 25(OHD) concentration was associated with CTTH. Intervention studies are required to find out if supplementation of vitamin D is effective in patients with CTTH." + "\n");
        result.add(a);

        a = new ArticleModel();
        a.setTitle( "Safety and efficacy of erenumab for preventive treatment of chronic migraine: a randomised, double-blind, placebo-controlled phase 2 trial.");
        a.setContent(a.getContent() + "The calcitonin gene-related peptide (CGRP) pathway is important in migraine pathophysiology. We assessed the efficacy and safety of erenumab, a fully human monoclonal antibody against the CGRP receptor, in patients with chronic migraine." + "\n");
        a.setContent(a.getContent() + "This was a phase 2, randomised, double-blind, placebo-controlled, multicentre study of erenumab for adults aged 18-65 years with chronic migraine, enrolled from 69 headache and clinical research centres in North America and Europe. Chronic migraine was defined as 15 or more headache days per month, of which eight or more were migraine days. Patients were randomly assigned (3:2:2) to subcutaneous placebo, erenumab 70 mg, or erenumab 140 mg, given every 4 weeks for 12 weeks. Randomisation was centrally executed using an interactive voice or web response system. Patients, study investigators, and study sponsor personnel were masked to treatment assignment. The primary endpoint was the change in monthly migraine days from baseline to the last 4 weeks of double-blind treatment (weeks 9-12). Safety endpoints were adverse events, clinical laboratory values, vital signs, and anti-erenumab antibodies. The efficacy analysis set included patients who received at least one dose of investigational product and completed at least one post-baseline monthly measurement. The safety analysis set included patients who received at least one dose of investigational product. The study is registered with ClinicalTrials.gov, number NCT02066415." + "\n");
        a.setContent(a.getContent() + "From April 3, 2014, to Dec 4, 2015, 667 patients were randomly assigned to receive placebo (n=286), erenumab 70 mg (n=191), or erenumab 140 mg (n=190). Erenumab 70 mg and 140 mg reduced monthly migraine days versus placebo (both doses -6·6 days vs placebo -4·2 days; difference -2·5, 95% CI -3·5 to -1·4, p<0·0001). Adverse events were reported in 110 (39%) of 282 patients, 83 (44%) of 190 patients, and 88 (47%) of 188 patients in the placebo, 70 mg, and 140 mg groups, respectively. The most frequent adverse events were injection-site pain, upper respiratory tract infection, and nausea. Serious adverse events were reported by seven (2%), six (3%), and two (1%) patients, respectively; none were reported in more than one patient in any group or led to discontinuation. 11 patients in the 70 mg group and three in the 140 mg group had anti-erenumab binding antibodies; none had anti-erenumab neutralising antibodies. No clinically significant abnormalities in vital signs, laboratory results, or electrocardiogram findings were identified. Of 667 patients randomly assigned to treatment, 637 completed treatment. Four withdrew because of adverse events, two each in the placebo and 140 mg groups." + "\n");
        a.setContent(a.getContent() + "In patients with chronic migraine, erenumab 70 mg and 140 mg reduced the number of monthly migraine days with a safety profile similar to placebo, providing evidence that erenumab could be a potential therapy for migraine prevention. Further research is needed to understand long-term efficacy and safety of erenumab, and the applicability of this study to real-world settings." + "\n");
        a.setContent(a.getContent() + "Amgen." + "\n");
        result.add(a);

        a = new ArticleModel();
        a.setTitle( "Challenging chronic migraine: targeting the CGRP receptor.");
        result.add(a);

        a = new ArticleModel();
        a.setTitle( "Targeted Peripheral Nerve-directed Onabotulinumtoxin A Injection for Effective Long-term Therapy for Migraine Headache..");
        a.setContent(a.getContent() + "Onabotulinumtoxin A (BOTOX) is an FDA-approved treatment for chronic migraine headaches (MHs) that involves on-label, high-dose administration across 31 anatomic sites. Anatomically specific peripheral nerve trigger sites have been identified that contribute to MH pathogenesis and are amenable to both BOTOX injection and surgical decompression. These sites do not always correlate with the on-label FDA-approved injection pattern, but represent a more targeted approach. The efficacy of peripheral nerve-directed BOTOX injection as an independent long-term therapeutic option has not been investigated." + "\n");
        a.setContent(a.getContent() + "The technique for peripheral nerve-directed therapeutic long-term BOTOX injection is described. A retrospective review was subsequently completed for 223 patients with MH. Sixty-six patients elected to proceed with diagnostic BOTOX injections. Of these, 24 continued long-term therapeutic BOTOX injections, whereas 42 matriculated to surgery. Outcomes were tracked." + "\n");
        a.setContent(a.getContent() + "Initial outcomes included significant improvement in migraine headache index (MHI) (53.5 ± 83.0, P < 0.006), headache days/mo (9.2 ± 12.7, P < 0.0009), and migraine severity (2.6 ± 2.5, P < 0.00008) versus baseline. MHI improved from the initiation of diagnostic injections to the establishment of steady-state injections (P < 0.002), and further improved over time (P < 0.05, mean follow-up 615 days) with no desensitization observed. Decompressive surgery resulted in significant improvement in MHI (100.8 ± 109.7, P < 0.0000005), headache days/mo (10.8 ± 12.7, P < 0.000002), migraine severity (3.0 ± 3.8, P < 0.00001), and migraine duration in hours (16.8 ± 21.6, P < 0.0007). MHI improvement with surgery was better than long-term BOTOX injections (P < 0.05)." + "\n");
        a.setContent(a.getContent() + "Though inferior to surgical decompression, preliminary data demonstrate that targeted peripheral nerve-directed BOTOX injection is an effective primary therapy for MH representing a possible alternative to nondirected BOTOX injection with decreased dosage requirements and potentially decreased cost." + "\n");
        result.add(a);

        a = new ArticleModel();
        a.setTitle( "Region-specific disruption of the blood-brain barrier following repeated inflammatory dural stimulation in a rat model of chronic trigeminal allodynia.");
        a.setContent(a.getContent() + "Background The blood-brain barrier (BBB) has been hypothesized to play a role in migraine since the late 1970s. Despite this, limited investigation of the BBB in migraine has been conducted. We used the inflammatory soup rat model of trigeminal allodynia, which closely mimics chronic migraine, to determine the impact of repeated dural inflammatory stimulation on BBB permeability. Methods The sodium fluorescein BBB permeability assay was used in multiple brain regions (trigeminal nucleus caudalis (TNC), periaqueductal grey, frontal cortex, sub-cortex, and cortex directly below the area of dural activation) during the episodic and chronic stages of repeated inflammatory dural stimulation. Glial activation was assessed in the TNC via GFAP and OX42 immunoreactivity. Minocycline was tested for its ability to prevent BBB disruption and trigeminal sensitivity. Results No astrocyte or microglial activation was found during the episodic stage, but BBB permeability and trigeminal sensitivity were increased. Astrocyte and microglial activation, BBB permeability, and trigeminal sensitivity were increased during the chronic stage. These changes were only found in the TNC. Minocycline treatment prevented BBB permeability modulation and trigeminal sensitivity during the episodic and chronic stages. Discussion Modulation of BBB permeability occurs centrally within the TNC following repeated dural inflammatory stimulation and may play a role in migraine." + "\n");
        result.add(a);

        a = new ArticleModel();
        a.setTitle( "Hypothalamus as a mediator of chronic migraine: Evidence from high-resolution fMRI.");
        a.setContent(a.getContent() + "To identify pathophysiologic mechanisms of migraine chronification using a recently standardized protocol for high-resolution brainstem imaging of trigeminal nociceptive stimulation." + "\n");
        a.setContent(a.getContent() + "Eighteen episodic migraineurs (EMs), 17 chronic migraineurs (CMs), and 19 healthy controls (HCs) underwent painful ammonia stimulation of the left nostril in a 3T MRI scanner. Functional images were acquired with a brainstem-optimized protocol for high-resolution echo-planar imaging." + "\n");
        a.setContent(a.getContent() + "We detected a significantly stronger activation of the anterior right hypothalamus in CMs compared to HCs. To exclude the headache as a prime mediator of the hypothalamic activations, we compared all migraineurs with headaches (EMs and CMs) with all migraineurs without headaches (EMs and CMs) and HCs in a second analysis and found a more posterior region of the hypothalamus to be more activated bilaterally during headaches." + "\n");
        a.setContent(a.getContent() + "Our data corroborate the fact that the hypothalamus plays a crucial role in the pathophysiology of migraine chronification and acute pain stage of migraineurs. While the more posterior part of the hypothalamus seems to be important for the acute pain stage, the more anterior part seems to play an important role in attack generation and migraine chronification." + "\n");
        result.add(a);

        return result;
    }

    public String getDate(ArticleSaxHandler context) {
        if(!date.isEmpty())
            return  date + "-";
        return date;
    }
}
